import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthServiceService} from '../auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthBlockGuard implements CanActivate {
  constructor(private authService: AuthServiceService, private router: Router) {
  }
  // tslint:disable-next-line:max-line-length
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/main']);
      return false;
    }
    return true;
  }
}
