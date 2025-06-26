
import { useState } from "react"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import { toast } from "react-toastify";



function Loginportal()  {
  const [isLogin, setIsLogin] = useState(true);
  const[showPassword, setShowPassword]=useState(false)
  const [Login, setLogindetails] = useState({
    user_name: "",
    phonenumber:" ",
     email:"",
    password:""
  } )


  const [errors, setErrors] = useState({
    user_name: "",
    phonenumber:"",
    email: "",
    password: "",
    general: ""
  })
  const navigate = useNavigate()
const handleforgetpassword=()=>{
navigate('/ChangePassword')
}
  const handlechange=(e)=>{
    const {name,value}=e.target;
    setLogindetails({
      ...Login,
      [name]: value
     })
      console.log(value);
      setErrors(prev => ({ ...prev, [name]: "" }))

  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = isLogin ? "http://localhost:8080/login" : "http://localhost:8080/register";
    try 
    {
      const response= await axios.post(url, Login,
      {
        method: "POST",
        headers:{
          'Content-Type':'application/json',
        }
        
      });
      console.log("Full response:", response);
      console.log(response.data);
      if (response.status === 200) {

        console.log(response.status)
        const  token  = response.data; 
        console.log("sending token",token)
        localStorage.setItem('jwtToken', token); 
        toast.success(isLogin ? "Login successful!" : "Registration successful!");
        if (isLogin) {
          navigate("/"); // Redirect to dashboard or another page after login
        } else {
          setIsLogin(true); // Switch to login form after successful registration
        }
       // toast.success("User logged in successfully!"); // Show success toast
        
      
      }
    } catch (error) {
      if (error.response) {
        console.log(error.response)
        const serverErrors = error.response.data;
        console.log(serverErrors);

        setErrors({
          email: serverErrors.email || "",
          password: serverErrors.password || "",
          general: serverErrors.general || ""
        });
      } else {
        setErrors(prev => ({
          ...prev,
          general: "",
         
        }));
        toast.error("An error occurred. Please try again later.");
      }
  }
};
const togglePasswordVisibility = () => {
  setShowPassword(!showPassword); // Toggle the state
};

  return (
    
      <div className="min-h-screen flex  flex-col   md:flex-row p-4" id="parentdiv">
       
        {/* Right Side */}
        <div className="w-full md:w-1/2 flex items-center justify-center">
          <form
            className="flex flex-col mb-3 bg-white p-6 sm:p-8 w-full rounded-lg shadow-lg max-w-sm"
            onSubmit={handleSubmit}
          >
            <div className="flex justify-between mb-4">
              <button
                type="button"
                className={`w-1/2 py-2 border mx-2 rounded-lg font-bold text-sm  input-box ${
                  isLogin ? "bg-blue-600 text-black" : "bg-white text-black"
                }`}
                onClick={() => setIsLogin(true)}
              >
                Login
              </button>
              <button
                type="button"
                className={`w-1/2 py-2 border mx-2 rounded-lg font-bold text-sm input-box ${
                  !isLogin ? "bg-blue-600 text-black" : "bg-white text-black"
                }`}
                onClick={() => setIsLogin(false)}
              >
                Register
              </button>
            </div>
  
            {!isLogin && (
              <div className="mb-4">
                <label htmlFor="user_name" className="block  text-gray-700 font-base mb-2 mt-3 font-semibold">
                  Name
                </label>
                <div className="flex items-center border rounded-lg input-box">
               
                  <input
                    type="text"
                    id="user_name"
                    name="user_name"
                    className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75 "
                    placeholder="Enter your name"
                    value={Login.user_name}
                    onChange={handlechange}
                    required
                  />
                </div>
                {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name}</p>}

                <label htmlFor="phonenumber" className="block  text-gray-700 font-base mb-2 mt-3 font-semibold">
                  Phone number
                </label>
                <div className="flex items-center border rounded-lg input-box mt-1">
                 
                  <input
                    type="text"
                    id="phonenumber"
                    name="phonenumber"
                    className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
                    placeholder="Enter your name"
                    value={Login.phonenumber}
                    onChange={handlechange}
                    required
                  />
                </div>
                {errors.name && <p className="text-red-500 text-sm mt-1">{errors.name}</p>}
              </div>
              
            )}
  
            <div className="mb-4">
              <label htmlFor="email" className="block  text-gray-700 font-base mb-2 mt-3 font-semibold">
                Email
              </label>
              <div className="flex items-center border rounded-lg input-box">
                <input
                  type="email"
                  id="email"
                  name="email"
                  className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
                  placeholder="Enter your email"
                  value={Login.email}
                  onChange={handlechange}
                  required
                />
              </div>
              {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email}</p>}
            </div>
  
            <div className="mb-4">
              <label htmlFor="password" className="block  text-gray-700 font-base mb-2 mt-3 font-semibold">
                Password
              </label>
              <div className="flex items-center border rounded-lg input-box">
                
                <input
                  type={showPassword?"text":"password"}
                  name="password"
                  id="password"
                  className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
                  placeholder="Enter your password"
                  value={Login.password}
                  onChange={handlechange}
                  required
                />
                <i
              className={`bi ${
              showPassword ? "bi-eye-slash" : "bi-eye"
              } text-gray-500 p-2 cursor-pointer`}
              onClick={togglePasswordVisibility} // Toggle on click
             ></i>
              </div>
              {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password}</p>}
            </div>
  
            <div className="flex  justify-between items-center mb-6">
             {
              isLogin ? (
                <>
                <div className="flex items-center">
                
                <input type="checkbox" id="checkbox" className="mr-2" required/>
                <p className="text-gray-700 font-medium">Remember Me</p>
              </div>
<button 
  className="text-[#81B29A] cursor-pointer bg-transparent border-none underline hover:text-[#6a9d7a] transition-colors"
  onClick={handleforgetpassword}
>
  Forgot Password?
</button>
                </>
              ) :(
                <>
                <div className="flex items-center ">
                <input type="checkbox" id="checkbox" className="mr-2" required/>
                <p clas sName="text-gray-700 font-medium">I agree to the Terms of Service and Privacy Policy</p>
              </div>
                </>)
             }</div>

  
            <button
              type="submit"
              className="w-full bg-blue-600 text-black  py-2 rounded-lg  " 
            >
              {isLogin ? "Sign In" : "Register"}
              
            </button>
            {
              isLogin ?(
                <>
                 <p className="text-center my-3 text-black">or continue with</p>
  
                <div className="flex justify-between">
                 <button className="w-1/2 py-2 bg-blue-400 text-black rounded-lg text-base mr-2 input-box hover:bg-slate-50 hover:font-bold">
                 <i className="bi bi-google p-2"></i>
                 Google
                </button>
                <button className="w-1/2 py-2 bg-blue-400 text-black rounded-lg  text-base ml-2 input-box  hover:bg-slate-50 hover:font-bold">
               <i className="bi bi-github p-2 text-base"></i>
                 Github
              </button>
               </div>

              {errors.general && <p className="text-red-500 text-center mt-4">{errors.general}</p>}
                </>
              ):(" ")
            }
           
          </form>
        </div>

             </div>
    );
  }
  


export default Loginportal

