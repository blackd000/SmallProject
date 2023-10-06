using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;

namespace ToDoList.Models;

public class MinimumAgeAttribute : ValidationAttribute, IClientModelValidator
{
    private readonly int _minYears;

    public MinimumAgeAttribute(int years) => _minYears = years;

    // custom server side validatin
    protected override ValidationResult IsValid(object? value, ValidationContext context)
    {
        if (value is DateTime dateToCheck)
        {
            dateToCheck = dateToCheck.AddYears(_minYears); // 2003 + 13

            if (dateToCheck <= DateTime.Today)
            {
                return ValidationResult.Success!; // it ok to be null
            }
        }

        return new ValidationResult(GetMsg(context.DisplayName ?? "Date"));
    }

    // add custom client side validation
    public void AddValidation(ClientModelValidationContext context)
    {
        if (!context.Attributes.ContainsKey("data-val"))
        {
            context.Attributes.Add("data-val", "true");
        }

        context.Attributes.Add("data-val-minimumage-years", _minYears.ToString());

        context.Attributes.Add("data-val-minimumage",
            GetMsg(context.ModelMetadata.DisplayName ?? context.ModelMetadata.Name ?? "Date"));
    }

    private string GetMsg(string name)
    {
        return ErrorMessage ?? $"{name} must be older than {_minYears}.";
    }
}