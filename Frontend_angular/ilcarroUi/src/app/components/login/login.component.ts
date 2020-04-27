import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { LoginModel } from '../../models/login-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginModel: LoginModel;
  errorMessage: string;
  constructor(private authService: AuthServiceService, private router: Router) {
    this.loginModel = new LoginModel();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.login(this.loginModel).subscribe(data => {
      if (data) {
        localStorage.setItem('userEmail', this.loginModel.email);
        this.errorMessage = null;
        this.router.navigate(['/profile']).then(() => {
          window.location.reload();
        });
      }
    }, error => {
      this.errorMessage = 'invalid username/password.';
    });
  }
}
