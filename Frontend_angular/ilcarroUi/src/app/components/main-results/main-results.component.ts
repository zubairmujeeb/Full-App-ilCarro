import { Observable } from 'rxjs';
import { UserService } from './../../services/user.service';

import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import * as $ from 'jquery';
import { SearchFilters } from '../../models/search';

@Component({
  selector: 'app-main-results',
  templateUrl: './main-results.component.html',
  styleUrls: ['./main-results.component.scss']
})
export class MainResultsComponent implements OnInit {

  pageable = {};
  filters: SearchFilters;
  cars: any;
  zoom: number;

  constructor(private router: Router, private userService: UserService, private ngZone: NgZone) {
    this.filters = new SearchFilters();
  }

  ngOnInit(): void {

    $.getScript('/assets/js/main.js');
    const ANGULAR_COMPONENT_REF = 'angularComponentReference';
    window[ANGULAR_COMPONENT_REF] = { component: this, zone: this.ngZone, loadAngularFunction: () => this.updatePriceFilters() };

    const data = history.state.data;
    if (data !== undefined) {
      this.filters = data.filters;
    }

    const sortDirection = this.filters.ascending ? 'asc' : 'desc';
    this.pageable = {
      page: 0,
      size: 10,
      sort: 'pricePerDay,' + sortDirection
    };

    this.filters.minAmount = '0';
    this.filters.maxAmount = '1000';

    this.search();
  }

  // Get Current Location Coordinates
  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.filters.latitude = position.coords.latitude;
        this.filters.longitude = position.coords.longitude;
        this.zoom = 15;
        this.search();
      });
    }
  }

  setSortDirectionAsc(): void {
    this.filters.ascending = true;
    this.search();
  }

  setSortDirectionDec(): void {
    this.filters.ascending = false;
    this.search();
  }

  updatePriceFilters(): void {
    this.filters.minAmount = (document.getElementById('minAmount') as HTMLInputElement).value;
    this.filters.maxAmount = (document.getElementById('maxAmount') as HTMLInputElement).value;

    this.search();
  }

  search(): void {

    this.userService.searchCar(this.getPostDataForSearch(), this.pageable).subscribe(data => {
      // @ts-ignore
      this.cars = data.dataList;
    }, err => {
      alert(err);
      this.cars = [];
    });

  }

  getPostDataForSearch() {
    // tslint:disable-next-line: prefer-const
    let postData: any = new Observable();
    if (this.filters.placeName !== undefined && this.filters.placeName !== '') {
      postData.placeName = this.filters.placeName;
    }
    if (this.filters.startDateTime !== undefined) {
      postData.startDateTime = new Date(this.filters.startDateTime).toISOString().substring(0, 10);
    }
    if (this.filters.endDateTime !== undefined) {
      postData.endDateTime = new Date(this.filters.endDateTime).toISOString().substring(0, 10);
    }
    if (this.filters.maxAmount !== undefined) {
      postData.maxAmount = this.filters.maxAmount;
    }
    if (this.filters.minAmount !== undefined) {
      postData.minAmount = this.filters.minAmount;
    }
    if (this.filters.make !== undefined && this.filters.make !== '') {
      postData.make = this.filters.make;
    }
    if (this.filters.modal !== undefined && this.filters.modal !== '') {
      postData.modal = this.filters.modal;
    }
    if (this.filters.year !== undefined && this.filters.year !== '') {
      postData.year = this.filters.year;
    }
    if (this.filters.engine !== undefined && this.filters.engine !== '') {
      postData.engine = this.filters.engine;
    }
    if (this.filters.fuel !== undefined && this.filters.fuel !== '') {
      postData.fuel = this.filters.fuel;
    }
    if (this.filters.transmission !== undefined && this.filters.transmission !== '') {
      postData.transmission = this.filters.transmission;
    }
    if (this.filters.wd !== undefined && this.filters.wd !== '') {
      postData.wd = this.filters.wd;
    }
    if (this.filters.horsepower !== undefined && this.filters.horsepower !== '') {
      postData.horsepower = this.filters.horsepower;
    }
    if (this.filters.fuelconsumption !== undefined && this.filters.fuelconsumption !== '') {
      postData.fuelconsumption = this.filters.fuelconsumption;
    }
    if (this.filters.longitude !== undefined) {
      postData.longitude = this.filters.longitude;
    }
    if (this.filters.latitude !== undefined) {
      postData.latitude = this.filters.latitude;
    }
    return postData;
  }

  onTabChange(tabname): void {

    this.filters = new SearchFilters();
    if (tabname === 'search') {
      this.filters.maxAmount = '1000';
      this.filters.minAmount = '0';
      this.search();
    } else if (tabname === 'filter') {
      this.search();
    } else if (tabname === 'location') {
      this.setCurrentLocation();
    }
  }

}
