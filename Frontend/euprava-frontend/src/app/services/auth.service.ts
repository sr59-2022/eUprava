import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginResponse {
  token: string;
  uloge: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  login(korisnickoIme: string, lozinka: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, {
      korisnickoIme,
      lozinka
    });
  }

  saveToken(token: string, uloge: string[]) {
    localStorage.setItem('token', token);
    localStorage.setItem('role', uloge.join(',')); // npr: "ROLE_ADMIN,ROLE_STUDENT"
  }


  getToken(): string | null {
    return localStorage.getItem('token');
  }


  getRoles(): string[] {
    const raw = localStorage.getItem('role');
    if (!raw) return [];
    return raw.split(',').map(r => r.trim()).filter(Boolean);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRoles().includes('ROLE_ADMIN');
  }
}
