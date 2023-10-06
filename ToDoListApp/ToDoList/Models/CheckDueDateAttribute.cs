using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;

namespace ToDoList.Models;

public class CheckDueDateAttribute : ValidationAttribute, IClientModelValidator
{
    private readonly string _dueDate;

    public CheckDueDateAttribute(string dueDate) => _dueDate = dueDate;

    protected override ValidationResult IsValid(object? value, ValidationContext context)
    {
        if (value is DateTime dueEnd)
        {
            // get property and then get value of that type
            var dueDateProperty = context.ObjectType.GetProperty(_dueDate);
            DateTime dueDateValue = (DateTime)dueDateProperty?.GetValue(context.ObjectInstance)!;

            if (dueEnd > dueDateValue)
            {
                return ValidationResult.Success!;
            }
        }

        return new ValidationResult(GetMsg(context.DisplayName ?? "DueEnd"));
    }

    public void AddValidation(ClientModelValidationContext context)
    {
        if (!context.Attributes.ContainsKey("data-val"))
        {
            context.Attributes.Add("data-val", "true");
        }

        context.Attributes.Add("data-val-duedate",
            GetMsg(context.ModelMetadata.DisplayName ?? context.ModelMetadata.Name ?? "DueDate"));
    }

    private string GetMsg(string name)
    {
        return ErrorMessage ?? $"{name} must be greater than {_dueDate}";
    }
}