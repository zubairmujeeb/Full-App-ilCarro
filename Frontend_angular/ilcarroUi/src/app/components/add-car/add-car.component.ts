import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {CarAdding} from '../../models/car-adding';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.scss']
})
export class AddCarComponent implements OnInit {
  car: CarAdding;
  constructor(private userService: UserService) {
    this.car = new CarAdding();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.addCar(this.car).subscribe(data => {
      this.car = data;
      console.log(this.car);
    });
  }

}
