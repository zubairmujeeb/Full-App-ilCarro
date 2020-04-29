import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { retry, catchError } from 'rxjs/operators';
import { CarAdding } from '../models/car-adding';
import { Observable, throwError } from 'rxjs';
import { Car } from '../models/car';
import { SearchFilters } from '../models/search';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  authToken;

  constructor(private http: HttpClient) {
    this.authToken = localStorage.getItem('Authorization');
  }

  getUser() {
    return this.http.get(environment.apiTarget + '/user', {
      headers: new HttpHeaders(
        {
          Authorization: this.authToken,
          Accept: '*/*',
        }),
    });
  }

  updateUser(user) {
    return this.http.put(environment.apiTarget + '/user', user, {
      headers: new HttpHeaders(
        {
          Authorization: this.authToken,
          Accept: '*/*',
        }),
    });
  }

  deleteUser() {
    return this.http.delete(environment.apiTarget + '/user', {
      headers: new HttpHeaders(
        {
          Authorization: this.authToken,
          Accept: '*/*',
        }),
    });
  }

  getMostPopularCars() {
    return this.http.get(environment.apiTarget + '/car/best', { observe: 'response' })
      .pipe(retry(1));
  }

  getLatestComments() {
    return this.http.get(environment.apiTarget + '/comments', { observe: 'response' })
      .pipe(retry(1));
  }

  addCar(carAdding: CarAdding): Observable<any> {
    return this.http.post(environment.apiTarget + '/car', carAdding, {
      headers: new HttpHeaders(
        {
          Authorization: this.authToken,
          Accept: '*/*',
        }),
    });
  }

  searchCar(postData, pageable) {
    debugger;
    // need to pass dynamic pagination value
    // tslint:disable-next-line: max-line-length
    return this.http.post<Car>(environment.apiTarget + '/search?' + 'page=' + pageable.page + '&size=' + pageable.size + '&sort=' + pageable.sort, postData);
  }
}
