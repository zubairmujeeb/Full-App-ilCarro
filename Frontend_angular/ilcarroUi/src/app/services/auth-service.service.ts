import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { User } from '../models/user';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { LoginModel } from '../models/login-model';
import { Auth } from '../models/auth';
import { environment } from '../../environments/environment';
import {catchError, mapTo, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  AUTH_TOKEN = 'Authorization';
  constructor(private http: HttpClient) {
  }

  register(user: User): Observable<any> {
    return this.http.post(environment.apiTarget + '/registration', user, { observe: 'response' });
  }

  login(loginModel: LoginModel): Observable<boolean> {
    return this.http.post<any>(environment.apiTarget + '/login', loginModel)
      .pipe(
        tap(authResult => this.setToken(authResult)),
        mapTo(true),
        catchError(err => this.handleError(err)));
  }

  handleError(err) {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${err.error.message}`;
    } else {
      // server-side error
      errorMessage = ` ${err.status}: ${err.message}`;
    }
    alert('invalid username/password');
    return throwError(errorMessage);
  }

  isLoggedIn() {
    return !!this.getAuthToken();
  }

  private setToken(authResult: Auth) {
    console.log(authResult.token);
    localStorage.setItem(this.AUTH_TOKEN, authResult.token);
  }

  private getAuthToken() {
    return localStorage.getItem(this.AUTH_TOKEN);
  }

  logOut() {
    localStorage.removeItem(this.AUTH_TOKEN);
    localStorage.removeItem('userEmail');
    // window.location.reload();
  }
}
