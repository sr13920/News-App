import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from '../../model/login';
import { User } from '../../model/user';
import { NewsService } from '../../service/service.service';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { CommonModule } from '@angular/common';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule,FormsModule, HttpClientModule,MatIconModule,MatFormFieldModule,MatInputModule,MatButtonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})

export class LoginComponent implements OnInit{

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);

  emailSignupFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordSignupFormControl = new FormControl('', [Validators.required,Validators.minLength(8)]);


  matcher = new MyErrorStateMatcher();
  errorMessage:String='';

  //loginObj: Login;
  signupObj:User;
  showLoginForm: boolean = true;
  showSignupForm:boolean=false;
  show:boolean=false

  constructor(private http: HttpClient,private router:Router,private service:NewsService) {
   // this.loginObj = new Login();
    this.signupObj=new User();
  }

  password() {
    this.show = !this.show;
}

login() {
  this.http
    .post('http://localhost:8087/auth/login', {
      "useremail": this.emailFormControl.value,
      "password": this.passwordFormControl.value
    })
    .subscribe(
      (data: any) => {
        if (data.token) {
          localStorage.setItem('token', data.token);
          this.service.getUseremail(data.token);
          this.router.navigateByUrl('/dashboard');
        } else {
          console.log('Login failed: Token not received');
        }
      },
      (error) => {
        if (error.status === 401) {
          this.errorMessage = 'Invalid email or password !';
        } else {
          console.error('Login failed:', error);
          this.errorMessage = 'An error occurred during login';
        }
      }
    );
}


  signup() {
    const userEmail = this.emailSignupFormControl.value;
    const pass=this.passwordSignupFormControl.value;
    if (userEmail && pass) { 
      this.signupObj.useremail = userEmail.toString();
      this.signupObj.password=pass.toString();
    }
    this.http
      .post('http://localhost:8087/user/saveUser', this.signupObj)
      .subscribe((data: any) => {
        console.log("signup : ", data);
        this.toggleForm();
      }, (error) => {
        console.error('signup failed', error);
      });
  }


  ngOnInit(): void {
  }

  toggleForm() {
    this.showLoginForm = !this.showLoginForm;
    this.showSignupForm=!this.showSignupForm
  }
}




