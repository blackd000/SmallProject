using Microsoft.AspNetCore.Mvc;
using ToDoList.Models;

namespace ToDoList.Controllers;

public class ValidationController : Controller
{
    private readonly ToDoListContext _context;

    public ValidationController(ToDoListContext context) => _context = context;

    // public JsonResult CheckEmail(string email)
    // {
    //     string msg = Check.EmailExists(_context, email);

    //     // if email is empty then email is already exists.
    //     if (string.IsNullOrEmpty(msg))
    //     {
    //         TempData["okEmail"] = true;
    //         return Json(true);
    //     }
    //     else
    //     {
    //         return Json(msg);
    //     }
    // }

    public JsonResult CheckTitle(string title)
    {
        string msg = Check.TitleExist(_context, title);

        if (string.IsNullOrEmpty(msg))
        {
            TempData["okToDoList"] = true;
            return Json(true);
        }
        else
        {
            return Json(msg);
        }
    }
}