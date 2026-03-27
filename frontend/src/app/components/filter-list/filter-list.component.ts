import { Component, OnInit } from '@angular/core';
import { Filter } from 'src/app/models/filter';
import { FilterService } from 'src/app/services/filter.service';

@Component({
  selector: 'app-filter-list',
  templateUrl: './filter-list.component.html',
  styleUrls: ['./filter-list.component.css']
})
export class FilterListComponent implements OnInit {

  filters: Filter[] = [];
  showDialog = false;
  selectedFilter: Filter | null = null;
  isModal: boolean = true;

  constructor(private filterService: FilterService) { }

  ngOnInit(): void {
    this.loadFilters();
  }

  loadFilters(): void {
    this.filterService.getFilters().subscribe((filters: Filter[]) => {
      this.filters = filters;
    });
  }

  openDialog(filter?: Filter): void {
    this.selectedFilter = filter || null;
    this.showDialog = true;
  }

  closeDialog(): void {
    this.showDialog = false;
    this.selectedFilter = null;
    this.filters = [];
    this.loadFilters();
  }

  deleteFilter(id: number): void {
    if (confirm('Are you sure you want to delete this filter?')) {
      this.filterService.deleteFilter(id).subscribe(() => {
        this.loadFilters();
      });
    }
  }
}