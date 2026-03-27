import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Filter, Criteria } from 'src/app/models/filter';
import { FilterService } from 'src/app/services/filter.service';

type CriteriaType = 'AMOUNT' | 'TITLE' | 'DATE';


const CONDITIONS: Record<CriteriaType, string[]> = {
  AMOUNT: ['EQUALS', 'GREATER_THAN', 'LESS_THAN', 'GREATER_OR_EQUAL', 'LESS_OR_EQUAL'],
  TITLE: ['CONTAINS', 'STARTS_WITH', 'ENDS_WITH'],
  DATE: ['EQUALS', 'BEFORE', 'AFTER', 'FROM', 'TO']
};

@Component({
  selector: 'app-filter-dialog',
  templateUrl: './filter-dialog.component.html',
  styleUrls: ['./filter-dialog.component.css']
})
export class FilterDialogComponent implements OnInit{

  @Input() filter: Filter | null = null;
  @Input() isModal: boolean = true;
  @Output() close = new EventEmitter<void>();

  filterName: string = '';
  criteriaList: Criteria[] = [];
  filterNameTouched: boolean = false;
  submitted: boolean = false;

  constructor(private filterService: FilterService) { }

  ngOnInit(): void {
    if (this.filter) {
      this.filterName = this.filter.name;
      this.criteriaList = this.filter.criteriaList.map(c => ({ ...c }));
    } else {
      this.criteriaList = [this.newCriteria()];
    }
  }
  newCriteria(): Criteria {
    return { type: 'AMOUNT', criteria_condition: 'EQUALS', criteria_value: '' };
  }

  addCriteria(): void {
    this.criteriaList.push(this.newCriteria());
  }

  removeCriteria(index: number): void {
    if (this.criteriaList.length > 1) {
      this.criteriaList.splice(index, 1);
    }
  }

  getConditions(type: CriteriaType): string[] {
    return CONDITIONS[type];
  }

  onTypeChange(criteria: Criteria): void {
    criteria.criteria_condition = CONDITIONS[criteria.type][0];
    criteria.criteria_value = '';
  }

  saveFilter(): void {
    this.submitted = true;
    if (!this.isValid()) return;

    const filterData: Filter = {
      ...this.filter,
      name: this.filterName.trim(),
      criteriaList: this.criteriaList
    };
    const request$ = filterData.id
      ? this.filterService.updateFilter(filterData)
      : this.filterService.createFilter(filterData);

      request$.subscribe(() => this.close.emit());
  }

  isValid(): boolean {
    if (this.filterName.trim().length === 0) return false;
    if (this.criteriaList.length === 0) return false;

    for (const criteria of this.criteriaList) {
      if(criteria.type === 'AMOUNT') {
        const num = Number(criteria.criteria_value);
        if (criteria.criteria_value === '' || isNaN(num)) return false;
        continue;
      }
      if (criteria.criteria_value.trim() === '') return false;
    } 
    return true;
  }

  onClose(): void {
    this.close.emit();
  }

  isNaN(value: number): boolean {
    return isNaN(value);
}

}
