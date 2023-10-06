namespace ToDoList.Models;

public class Filters
{
    public Filters(string? filterString)
    {
        FilterString = filterString ?? "all-all-all";

        string[] filters = FilterString.Split('-');
        CategoryId = filters[0];
        Due = filters[1];
        StatusId = filters[2];
    }

    public string FilterString { get; }
    public string CategoryId { get; }
    public string Due { get; }
    public string StatusId { get; }

    // Allow whether the user wants to filter by one of the filter criteria
    // like if not "all" it indicate that the user has selected a value to filter
    // by, and the property is set to true
    public bool HasCategory => CategoryId.ToLower() != "all";
    public bool HasDue => Due.ToLower() != "all";
    public bool HasStatus => StatusId.ToLower() != "all";

    // This dictionary holds the values that appear in the Due drop-down list. I use
    // a Dictionary instead of List is because Uppercase is for display in the Due drop
    // down and Lowercase is for retrieving form the id route
    public static Dictionary<string, string> DueFilterValues =>
        new()
        {
            { "future", "Future" },
            { "past", "Past" },
            { "current", "Current" }
        };
    public bool IsPast => Due.ToLower() == "past";
    public bool IsFuture => Due.ToLower() == "future";
    public bool IsCurrent => Due.ToLower() == "current";
}