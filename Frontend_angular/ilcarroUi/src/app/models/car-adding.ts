import {PickUpPlace} from './pick-up-place';
import {User} from './user';

export class CarAdding {
  serialNumber: string;
  make: string;
  modal: string;
  year: string;
  engine: string;
  fuel: string;
  gear: string;
  wheelsDrive: string;
  doors: number;
  seats: number;
  fuelConsumption: number;
  features: string[];
  carClass: string;
  pricePerDay: string;
  distanceIncluded: number;
  about: string;
  pickUpPlace: PickUpPlace;
  imageUrl: string[];
  userId: number;
}
