import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ObavestenjeDTO } from '../model/obavestenje.model';

@Injectable({
  providedIn: 'root'
})
export class ObavestenjeService {

  private apiUrl = 'http://localhost:8082/api/obavestenja';

  constructor(private http: HttpClient) {}

  getObavestenja(): Observable<ObavestenjeDTO[]> {
    return this.http.get<ObavestenjeDTO[]>(`${this.apiUrl}/poslodavac`);
  }

  oznaciProcitano(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/procitano`, {});
  }

  getBrojNeprocitanih(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/neprocitana`);
  }
}
