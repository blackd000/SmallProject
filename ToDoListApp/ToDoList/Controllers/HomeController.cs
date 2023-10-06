using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ToDoList.Models;

namespace ToDoList.Controllers;

public class HomeController : Controller
{
    private readonly ToDoListContext _context;

    public HomeController(ToDoListContext context) => _context = context;

    public IActionResult Index(string id)
    {
        ToDoListViewModel model = new()
        {
            Filters = new(id),
            Categories = _context.Categories.ToList(),
            Statuses = _context.Statuses.ToList()
        };

        ToDoListSession session = new(HttpContext.Session);
        session.SetFilterString(model.Filters.FilterString);
        
        IQueryable<ToDo> query = _context.ToDos
            .Include(t => t.Category)
            .Include(t => t.Status);
        if (model.Filters.HasCategory)
        {
            query = query.Where(t => t.CategoryId == model.Filters.CategoryId);
        }
        if (model.Filters.HasStatus)
        {
            query = query.Where(t => t.StatusId == model.Filters.StatusId);
        }
        if (model.Filters.HasDue)
        {
            DateTime today = DateTime.Today;
            if (model.Filters.IsPast)
            {
                query = query.Where(t => t.DueDate.Value.Date < today);
            }
            else if (model.Filters.IsCurrent)
            {
                query = query.Where(t => t.DueDate.Value.Date == today);
            }
            else if (model.Filters.IsFuture)
            {
                query = query.Where(t => t.DueDate.Value.Date > today);
            }
        }
        model.ToDos = query.OrderBy(t => t.DueDate).ToList();

        return View(model);
    }

    [HttpPost]
    public IActionResult Filter(string[] filter)
    {
        string id = string.Join("-", filter);
        return RedirectToAction("Index", new { id }); // id has the same name so it works
    }
}