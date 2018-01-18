import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';

import {UserInfoService} from "./user-info.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userInfo: string;
  isLoading: boolean;

  constructor(private userInfoService: UserInfoService) { }

  ngOnInit() {
    this.isLoading = true;
    this.userInfoService.getUserInfo()
      .pipe(finalize(() => { this.isLoading = false; }))
      .subscribe((username: string) => { this.userInfo = username; });
  }

}
