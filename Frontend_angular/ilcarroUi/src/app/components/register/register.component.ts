import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {AuthServiceService} from "../../services/auth-service.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  constructor(private authServiceService: AuthServiceService, private  router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
      this.authServiceService.register(this.user).subscribe(data => {
        if (data.body.message === 'User already exists.') {
            this.errorMessage = 'Email already exists.';
        } else {
          this.errorMessage = null;
          this.router.navigate(['/login']);
        }
      }, err => {

      });
  }

}
