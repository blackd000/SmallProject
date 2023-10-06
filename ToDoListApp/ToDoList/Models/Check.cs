namespace ToDoList.Models;

public static class Check
{
    // public static string EmailExists(ToDoListContext context, string email)
    // {
    //     string msg = string.Empty;
    //     if (!string.IsNullOrEmpty(email))
    //     {
    //         User? user = context.Users
    //             .FirstOrDefault(u => u.Email.ToLower() == email.ToLower());
    //         if (user is not null)
    //         {
    //             msg = $"email address {email} has already in use.";
    //         }
    //     }
    //     return msg;
    // }

    public static string TitleExist(ToDoListContext context, string title)
    {
        string msg = string.Empty;
        if (!string.IsNullOrEmpty(title))
        {
            ToDo? toDo = context.ToDos.FirstOrDefault(t => t.Title == title);
            if (toDo is not null)
            {
                msg = $"this title of the to-do list has already in use.";
            }
        }
        return msg;
    }
}