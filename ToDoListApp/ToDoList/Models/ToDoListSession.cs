namespace ToDoList.Models;

public class ToDoListSession
{
    private const string FilterStringKey = "filterkey";

    private readonly ISession _session;

    public ToDoListSession(ISession session)
    {
        _session = session;
    }

    public void SetFilterString(string filterString) => 
        _session.SetString(FilterStringKey, filterString);
    public string GetFilterString() => 
        _session.GetString(FilterStringKey) ?? string.Empty;
}