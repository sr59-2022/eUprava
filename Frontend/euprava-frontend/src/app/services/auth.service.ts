import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';


export interface LoginResponse {
  token: string;
  uloge: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';

  private loggedIn = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  loggedIn$ = this.loggedIn.asObservable();


  constructor(private http: HttpClient) { }


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
    localStorage.setItem('role', uloge.join(','));
    this.loggedIn.next(true);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role'); // 'ADMIN', 'GRADJANIN', 'POSLODAVAC'
  }

  isAdmin(): boolean {
    const role = this.getRole();
    return role?.includes('ADMIN') ?? false;
  }

  isGradjanin(): boolean {
    const role = this.getRole();
    return role?.includes('GRADJANIN') ?? false;
  }

  isPoslodavac(): boolean {
    const role = this.getRole();
    return role?.includes('POSLODAVAC') ?? false;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

}

