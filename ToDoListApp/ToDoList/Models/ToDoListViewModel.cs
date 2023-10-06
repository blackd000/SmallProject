namespace ToDoList.Models;

public class ToDoListViewModel
{
    public Filters Filters { get; set; } = new Filters("all-all-all");
    public ToDo CurrentTask { get; set; } = new();

    public List<Category> Categories { get; set; } = new();
    public List<Status> Statuses { get; set; } = new();
    public List<ToDo> ToDos { get; set; } = new();

    public Dictionary<string, string> DueFilters { get; set; } = Filters.DueFilterValues;
}