import { useState } from "react"
import { useNavigate } from "react-router"
import "./Styling/login.css"
import LoginForm from "../components/LoginForm";


export default function Login(){
    const navigate = useNavigate();


    const [login, setLogin] = useState({
        "username":"",
        "password":""
    })
    

    const changeUserDetails = (e) => {
        console.log(login);
        setLogin({...login,[e.target.name]:e.target.value})
    }

    const handleLogin = (e) => {
        e.preventDefault()
        fetch(`http://localhost:8787/users/login`, {
            method : "POST",
            headers : {
                "Content-Type":"application/json"
            },
            body : JSON.stringify(login)
        })
        .then((res) => {
            if(res.ok){
                // This is where the token is being saved for future use
                return res.json()
            }
            else{
                throw new Error("Incorrect Username or Password");
            }
        })
        .then((data) => {
            sessionStorage.setItem("token", JSON.stringify(data))
            navigate("/breeding-lines")
        })
        .catch((err) => {
            console.error("Error: ", err)
        })


    }

    return(<>

        {/* <div className="loginForm">
        <form onSubmit={handleLogin} method="post">
            <label>Username: 
                <input required name="username" onChange={changeUserDetails} type="text" />
            </label>
            <br/>
            <label>Password: 
                <input required name="password" onChange={changeUserDetails} type="password" />
            </label>

            <button>Login</button>
        </form>

            <p><a href="">Forgot Password</a></p>
            <a href="/registar"><button type="button">Create an Account</button></a>
        </div> */}

        <LoginForm handleLogin={handleLogin} changeUserDetails={changeUserDetails}></LoginForm>

    </>)

}