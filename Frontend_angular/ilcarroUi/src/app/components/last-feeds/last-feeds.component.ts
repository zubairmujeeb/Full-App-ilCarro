import { Component, OnInit } from '@angular/core';
import {UserService} from '../../services/user.service';
import {Comments} from '../../models/comments';

@Component({
  selector: 'app-last-feeds',
  templateUrl: './last-feeds.component.html',
  styleUrls: ['./last-feeds.component.scss']
})
export class LastFeedsComponent implements OnInit {
  comments: Comments[];
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.latestComments();
  }

  latestComments() {
    this.userService.getLatestComments().subscribe(data => {
      // @ts-ignore
      this.comments = data.body.dataList;
    });
  }
}
