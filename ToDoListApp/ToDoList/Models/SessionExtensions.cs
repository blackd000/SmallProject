using System.Text.Json;

namespace ToDoList.Models;

public static class SessionExtensions
{
    public static void SetObject<T>(this ISession session, string key, T value)
    {
        session.SetObject(key, JsonSerializer.Serialize(value));
    }

    public static T? GetObject<T>(this ISession session, string key)
    {
        string? json = session.GetString(key);
        if (string.IsNullOrEmpty(json))
        {
            return default;
        }
        else
        {
            return JsonSerializer.Deserialize<T>(json);
        }
    }
}