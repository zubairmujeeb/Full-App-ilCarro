import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import {UserService} from '../../../services/user.service';
import {AuthServiceService} from '../../../services/auth-service.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  constructor(private userService: UserService, private authService: AuthServiceService,
              private router: Router) { }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem('editUser'));
  }
  updateUser() {
    this.userService.updateUser(this.user).subscribe(response => {
      // @ts-ignore
      console.log(response);
      // @ts-ignore
      this.router.navigate(['/profile']);
    }, error => {
      alert(error.message);
    });
  }
  deleteUser() {
    this.userService.deleteUser().subscribe(response => {
      // @ts-ignore
      alert(response.message);
      this.authService.logOut();
      this.router.navigate(['/']);
    }, error => {
      alert(error.message);
    });
  }


}
