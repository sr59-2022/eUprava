import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export enum StatusNezaposlenosti {
  ZAPOSLEN = 'ZAPOSLEN',
  NEZAPOSLEN = 'NEZAPOSLEN',
  STUDENT = 'STUDENT'
}

export interface Gradjanin {
  id: number;
  ime: string;
  prezime: string;
  jmbg: string | null;
  statusNezaposlenosti: StatusNezaposlenosti;
  authGradjaninId: number;
}


@Injectable({
  providedIn: 'root'
})
export class GradjaninService {
  private apiUrl = 'http://localhost:8082/api/gradjanin';

  constructor(private http: HttpClient) {}

  getMe(): Observable<Gradjanin> {
    return this.http.get<Gradjanin>(`${this.apiUrl}/me`);
  }

  updateMe(gradjanin: Gradjanin): Observable<Gradjanin> {
    return this.http.put<Gradjanin>(`${this.apiUrl}/me`, gradjanin);
  }
}
