using Microsoft.AspNetCore.Mvc;
using ToDoList.Models;

namespace ToDoList.Controllers;

public class ToDoListController : Controller
{
    private readonly ToDoListContext _context;

    public ToDoListController(ToDoListContext context) => _context = context;

    [HttpGet]
    public IActionResult Add()
    {
        ViewBag.Action = "Add";

        ToDoListViewModel model = new()
        {
            Categories = _context.Categories.ToList(),
            Statuses = _context.Statuses.ToList(),
            CurrentTask = new ToDo { StatusId = "open" }
        };
        return View("AddUpdate", model);
    }

    [HttpGet]
    public IActionResult Edit(int id)
    {
        ViewBag.Action = "Update";

        ToDoListViewModel model = new()
        {
            Categories = _context.Categories.ToList(),
            Statuses = _context.Statuses.ToList(),
            CurrentTask = _context.ToDos.Find(id) ?? new ToDo()
        };
        return View("AddUpdate", model);
    }

    [HttpPost]
    public IActionResult Edit(ToDoListViewModel model)
    {
        // once again, check it in the server side, but I don't know why
        // the Remote validation not working rn.
        if (TempData["okToDoList"] is null)
        {
            string msg = Check.TitleExist(_context, model.CurrentTask.Title);
            if (!string.IsNullOrEmpty(msg))
            {
                ModelState.AddModelError(nameof(model.CurrentTask.Title), msg);
            }
        }

        // same this is to check server side validation
        if (ModelState.IsValid)
        {
            if (model.CurrentTask.ToDoId == 0)
            {
                _context.ToDos.Add(model.CurrentTask);
            }
            else
            {
                _context.ToDos.Update(model.CurrentTask);
            }
            _context.SaveChanges();

            return RedirectToAction("Index", "Home");
        }
        else
        {
            model.Categories = _context.Categories.ToList();
            model.Statuses = _context.Statuses.ToList();
            model.CurrentTask = new ToDo { StatusId = "open" };

            ViewBag.Action = model.CurrentTask.ToDoId == 0 ? "Add" : "Update";

            return View("AddUpdate", model);
        }
    }

    [HttpGet]
    public IActionResult Delete(ToDo task)
    {
        ToDoListSession session = new(HttpContext.Session);
        string id = session.GetFilterString(); // the asp-route-... cant be the same with this one

        _context.ToDos.Remove(task);
        _context.SaveChanges();

        TempData["message"] = "Successfully deleted!!";

        return RedirectToAction("Index", "Home", new { id });
    }

    [HttpPost]
    public IActionResult MarkComplete([FromRoute] string id, ToDo selected)
    {
        // NOTE: this is only for testing... I can use session to make it easier
        // Include this attribute [FromRoute] is necessary because ToDo also has a property named Id
        // (*now its gone) that sent to this action method in the POST request. Without this, MVC
        // will would look in the POST request first, find the name Id, bind it, skip other checks.

        selected = _context.ToDos.Find(selected.ToDoId)!; // use null-forgiving operator
        if (selected is not null)
        {
            selected.StatusId = "closed";
            _context.SaveChanges();
        }

        return RedirectToAction("Index", "Home", new { id });
    }

    [HttpGet]
    public IActionResult MarkUncomplete(ToDo selected)
    {
        ToDoListSession session = new(HttpContext.Session);
        string id = session.GetFilterString();

        selected = _context.ToDos.Find(selected.ToDoId)!;
        if (selected is not null)
        {
            selected.StatusId = "open";
            _context.SaveChanges();
        }

        return RedirectToAction("Index", "Home", new { id });
    }

    [HttpPost]
    public IActionResult DeleteCompleteTasks(string id)
    {
        List<ToDo> tasks = _context.ToDos
            .Where(t => t.StatusId == "closed")
            .ToList();

        foreach (ToDo task in tasks)
        {
            _context.ToDos.Remove(task);
        }
        _context.SaveChanges();

        TempData["message"] = "Successfully deleted all complete tasks!!";

        return RedirectToAction("Index", "Home", new { id });
    }
}