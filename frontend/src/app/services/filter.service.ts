import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Filter } from '../models/filter';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  private apiUrl = 'http://localhost:8080/api/filters';
  constructor(private http: HttpClient) { }

  getFilters(): Observable<Filter[]> {
    return this.http.get<Filter[]>(this.apiUrl);
  }

  createFilter(filter: Filter): Observable<Filter> {
    return this.http.post<Filter>(this.apiUrl, filter);
  }

  updateFilter(filter: Filter): Observable<Filter> {
    return this.http.put<Filter>(`${this.apiUrl}/${filter.id}`, filter);
  }

  deleteFilter(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
