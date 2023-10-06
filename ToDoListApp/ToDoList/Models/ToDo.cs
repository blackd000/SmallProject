using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;

namespace ToDoList.Models;

public class ToDo
{
    public int ToDoId { get; set; }

    [Required(ErrorMessage = "Pls enter a title")]
    [Remote("CheckTitle", "Validation")]
    public string Title { get; set; } = string.Empty;

    [Required(ErrorMessage = "Pls ener a due date")]
    public DateTime? DueDate { get; set; }

    [Required(ErrorMessage = "Pls ener a due date")]
    [CheckDueDate("DueDate")]
    public DateTime? DueEnd { get; set; }

    [Required(ErrorMessage = "Pls select a category")]
    public string CategoryId { get; set; } = string.Empty;
    [ValidateNever]
    public virtual Category Category { get; set; } = null!;

    [Required(ErrorMessage = "Pls select a status")]
    public string StatusId { get; set; } = string.Empty;
    [ValidateNever]
    public virtual Status Status { get; set; } = null!;

    public bool OverDue => StatusId == "open" && DueEnd < DateTime.Now;
}