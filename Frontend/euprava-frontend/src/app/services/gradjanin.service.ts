import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Gradjanin {
  id: number;
  ime: string;
  prezime: string;
  jmbg: string | null;
  statusNezaposlenosti: string;
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
}
