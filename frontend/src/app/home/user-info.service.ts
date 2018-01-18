import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserInfoService {
  constructor(private http: HttpClient) { }

  getUserInfo() : Observable<string> {
    return this.http.get("/api/user", {responseType: 'text'});
  }
}
