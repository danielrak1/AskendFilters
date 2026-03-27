import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'conditionLabel'
})
export class ConditionLabelPipe implements PipeTransform {

  transform(value: string): string {
    return value
    .replace(/_/g, ' ')
    .toLowerCase()
    .replace(/^\w/, c => c.toUpperCase())
  }

}
