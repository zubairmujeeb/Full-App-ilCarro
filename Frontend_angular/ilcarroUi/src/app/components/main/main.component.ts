import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UserService} from '../../services/user.service';
import {SearchFilters} from '../../models/search';
import {Car} from '../../models/car';
import * as $ from 'jquery';
import {DatePipe} from '@angular/common';

// noinspection AngularMissingOrInvalidDeclarationInModule
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  filters: SearchFilters;
  cars: Car;
  postData = {};
  constructor(private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.filters = new SearchFilters();
  }

  ngOnInit(): void {
    $.getScript('../../../assets/js/main.js');
  }

  onSubmit(): void {
    this.postData = {
      placeName: this.filters.placeName,
      // startDateTime: new Date(this.datepipe.transform(this.filters.startDateTime, 'dd/MM/yyyy')),
      // endDateTime: new Date(this.datepipe.transform(this.filters.endDateTime, 'dd/MM/yyyy')),
      startDateTime: new Date(this.filters.startDateTime).toISOString().substring(0, 10),
      endDateTime: new Date(this.filters.endDateTime).toISOString().substring(0, 10),
    };
    this.userService.searchCar(this.postData).subscribe(response => {
      // @ts-ignore
      // this.cars = response.dataList;
      this.router.navigate(['/results'], {state : { data: { filters: this.filters, cars: response.dataList}}});
    }, error => {
      alert(error.message);
    });
  }
}
