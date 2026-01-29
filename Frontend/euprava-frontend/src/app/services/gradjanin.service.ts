import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {Gradjanin} from '../model/gradjanin.model';
import {GradjaninDTO} from '../model/gradjanin.model';



@Injectable({
  providedIn: 'root'
})
export class GradjaninService {
  private apiUrl = 'http://localhost:8082/api/gradjanin';

  constructor(private http: HttpClient) {
  }

  getMe(): Observable<Gradjanin> {
    return this.http.get<Gradjanin>(`${this.apiUrl}/me`);
  }

  updateMe(gradjanin: Gradjanin): Observable<Gradjanin> {
    return this.http.put<Gradjanin>(`${this.apiUrl}/me`, gradjanin);
  }

  getProfilSaPotvrdom(): Observable<GradjaninDTO> {
    return this.http.get<GradjaninDTO>(`${this.apiUrl}/me/dto`);
  }
}
