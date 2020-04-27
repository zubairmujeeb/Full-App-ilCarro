import { Component, OnInit } from '@angular/core';
import {AuthServiceService} from '../../services/auth-service.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor(private authService: AuthServiceService) { }

  ngOnInit() {
  }

}
