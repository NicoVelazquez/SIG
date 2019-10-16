import {AbstractControl} from '@angular/forms';

export class DateValidation {

  static GreaterThanDate(AC: AbstractControl) {
    const startDate = AC.get('startDate').value; // to get value in input tag
    const finishDate = AC.get('finishDate').value; // to get value in input tag
    if (startDate >= finishDate) {
      AC.get('finishDate').setErrors({MatchPassword: true});
    } else {
      return null;
    }
  }
}
