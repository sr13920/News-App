import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { NewsService } from '../../service/service.service';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpClient: HttpClient;
  let newsService: NewsService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
     // declarations: [LoginComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        RouterTestingModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule
      ],
      providers: [
        NewsService 
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    httpClient = TestBed.inject(HttpClient);
    newsService = TestBed.inject(NewsService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form controls with empty strings', () => {
    expect(component.emailFormControl.value).toBe('');
    expect(component.passwordFormControl.value).toBe('');
  });

  it('should toggle between login and signup forms', () => {
    const initialLoginFormState = component.showLoginForm;
    const initialSignupFormState = component.showSignupForm;
    component.toggleForm();
    expect(component.showLoginForm).toEqual(!initialLoginFormState);
    expect(component.showSignupForm).toEqual(!initialSignupFormState);
  });

  // it('should call login() method and handle successful login', () => {
  //   spyOn(httpClient, 'post').and.returnValue(of({ token: 'mock-token' }));
  //   spyOn(component.router, 'navigateByUrl').and.stub();
  //   component.emailFormControl.setValue('test@example.com');
  //   component.passwordFormControl.setValue('password123');
  //   component.login();
  //   expect(httpClient.post).toHaveBeenCalledOnceWith(
  //     'http://localhost:8087/auth/login',
  //     { useremail: 'test@example.com', password: 'password123' }
  //   );
  //   expect(localStorage.getItem('token')).toEqual('mock-token');
  //   expect(component.router.navigateByUrl).toHaveBeenCalledWith('/dashboard');
  // });

  it('should handle failed login', () => {
    spyOn(httpClient, 'post').and.returnValue(throwError({ status: 401 }));
    component.login();
    expect(component.errorMessage).toEqual('Invalid email or password !');
  });

  it('should call signup() method and handle successful signup', () => {
    spyOn(httpClient, 'post').and.returnValue(of({ id: '1', username: 'testuser' }));
    spyOn(console, 'error').and.stub();
    component.signupObj = { firstname: 'Test', lastname: 'User', mobile: '1234567890', useremail: 'test@example.com', password: 'password123' };
    component.signup();
    expect(httpClient.post).toHaveBeenCalledOnceWith(
      'http://localhost:8087/user/saveUser',
      { firstname: 'Test', lastname: 'User', mobile: '1234567890', useremail: 'test@example.com', password: 'password123' }
    );
    expect(console.error).not.toHaveBeenCalled();
  });

  it('should handle failed signup', () => {
    spyOn(httpClient, 'post').and.returnValue(throwError('Signup failed'));
    spyOn(console, 'error').and.stub();
    component.signup();
    expect(console.error).toHaveBeenCalledWith('signup failed', 'Signup failed');
  });
});
