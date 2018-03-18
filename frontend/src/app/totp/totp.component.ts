import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {finalize} from 'rxjs/operators';

import {environment} from '../../environments/environment';
import {Logger} from '../core/logger.service';
import {AuthenticationService} from '../core/authentication/authentication.service';
import {AuthenticationTokenService} from "../core/authentication/authentication-token.service";

const log = new Logger('Totp');

@Component({
  selector: 'app-totp',
  templateUrl: './totp.component.html',
  styleUrls: ['./totp.component.scss']
})
export class TotpComponent implements OnInit {

  version: string = environment.version;
  error: string;
  totpForm: FormGroup;
  isLoading = false;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService) {
    this.createForm();
  }

  ngOnInit() {
  }

  totp() {
    this.isLoading = true;
    this.authenticationService
      .loginSecurityStep({
        authenticationToken: AuthenticationTokenService.credentials.token,
        securityStep: "TOTP",
        password: this.totpForm.value.password
      })
      .pipe(finalize(() => {
        this.totpForm.markAsPristine();
        this.isLoading = false;
      }))
      .subscribe(credentials => {
        log.debug(`${credentials.username} successfully completed TOTP security step`);
        this.router.navigate(['/'], {replaceUrl: true});
      }, error => {
        log.debug(`Totp error: ${error}`);
        this.error = error;
      });
  }

  private createForm() {
    this.totpForm = this.formBuilder.group({
      password: ['', Validators.required]
    });
  }

}
