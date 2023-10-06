JQuery.validator.addMethod("minimumage", (value, element, param) => {
    // get data enter by user, comfirm its a date
    if (value === '') {
        return false;
    }

    let dateToCheck = new Date(value);
    if (dateToCheck === "Invalid Date") {
        return false;
    }

    // get the number of years
    let minYears = Number(param); // convert to Number

    dateToCheck.setFullYear(dateToCheck.getFullYear() + minYears);

    // get the current 
    let today = new Date();

    return dateToCheck <= today;
});