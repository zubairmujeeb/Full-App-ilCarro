import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from "../../services/auth-service.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  authToken

  constructor(private authService: AuthServiceService) { }

  ngOnInit() {
    this.authToken = sessionStorage.getItem('Authorization');
  }
}
