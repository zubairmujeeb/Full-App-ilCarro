import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User;
  errorMessage: string;
  constructor(private userService: UserService, private router: Router) {
    this.user = new User();
  }

  ngOnInit() {
    this.userService.getUser().subscribe(response => {
      this.user.email = localStorage.getItem('userEmail');
        // @ts-ignore
      this.user.firstName = response.dataList[0].firstName;
        // @ts-ignore
      this.user.secondName = response.dataList[0].secondName;
      }, error => {
        this.errorMessage = 'Some error while getting User profile';
      });
  }

  editUser() {
    sessionStorage.setItem('editUser', JSON.stringify(this.user));
  }
}
