import React, { useState } from 'react'
import './TransportDestinations.css'
import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';



function TransportDestinations({ data, setData,onNext }) {
  const [fromTransportLocation,setFromTransportLocation]=useState(
    {
      source:"",
      destination:"",
    }
  );
  const [fromLocationSuggestions, setFromLocationSuggestions] = useState([]);
  const [toLocationSuggestions, setToLocationSuggestions] = useState([]);
   const navigate=useNavigate();
  
  const [errors, setErrors] = useState({
    source: "",
    destination: "",
    general: "",
  })
  const handleSuggestionClick = (field, suggestion) => {
    setFromTransportLocation(prevState => ({
        ...prevState,
        [field]: suggestion
    }));
    if (field === "source") {
      setFromLocationSuggestions([]);
  } else if (field === "destination") {
      setToLocationSuggestions([]);
  }

  }
  const handlechange=async(e)=>{
    const{name,value}=e.target;
    setFromTransportLocation({
      ...fromTransportLocation,
      [name]:value
    });

     // Clear any previous errors for this input

    setErrors((prev) => ({ ...prev, [name]: "" }))

    if(value.trim().length>=3){
      try{
        const token = localStorage.getItem('jwtToken'); 
      const response=await axios.get(`http://localhost:8080/cities?query=${value}`,
       {
          headers: {
           "Authorization": `Bearer ${token}` ,// ⬅️ send token in header
             "Content-Type": "application/json"
          }
        }
      );
      if(name==="source"){
        setFromLocationSuggestions(response.data);
      }else{
        setToLocationSuggestions(response.data);
      }
    }catch(error){
      if(error.response.status===401){
        navigate('/Loginportal')
      }
      console.error('Error fetching city suggestions:', error);
    }
    }
    else {
      if (name === 'source') {
          setFromLocationSuggestions([]);
      } else if (name === 'destination') {
          setToLocationSuggestions([]);
      }
  }
  }
  
  useEffect(() => {
     // Populate form with existing data when entering this step
    if (data) {
      setFromTransportLocation({
        source: data.source || "",
        destination: data.destination || "",
      })
    }
  }, [data])

  const handlesubmit=async(e)=>{
    e.preventDefault(); 
    let isValid = true
    const newErrors = { source: "", destination: "", general: "" }

    if (!fromTransportLocation.source.trim()) {
      newErrors.source = "Source location is required"
      isValid = false
    }

    if (!fromTransportLocation.destination.trim()) {
      newErrors.destination = "Destination location is required"
      isValid = false
    }

    if (
      fromTransportLocation.source === fromTransportLocation.destination &&
      fromTransportLocation.source.trim() !== ""
    ) {
      newErrors.general = "Source and destination cannot be the same"
      isValid = false
    }

    if (!isValid) {
      setErrors(newErrors)
      return
    }

    // Update the parent component's data
    setData((prev) => ({
      ...prev,
      ...fromTransportLocation,
    }))

    // Call onNext to move to the next step
    onNext(fromTransportLocation)
      // const response=await axios.post('http://localhost:8080/savetransportdestination ',fromTransportLocation) 
      // console.log("Data sent successfully");
      // console.log(response.data);
      // toast.success("location enters successfully");
      
    
  }
  return (
    <div >

      {/* general error */}
      {errors.general && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">{errors.general}</div>}

      <div className=" flex justify-center  items-center  ">
      <form className="h-[600px] w-[500px] flex flex-col  mb-3 pr-20 bg-white p-6 sm:p-8  rounded-lg shadow-lg max-w-sm " onSubmit={handlesubmit}>
     {/* Book your truck heading */}
      <div className="mt-2  ">
       <h1 className='lg:text-3xl md:text-2xl text-xl  text-center'><b>Book Your Truck</b></h1>
    
        {/* <!-- Source Input --> */}
     <div className="flex flex-col space-y-1 md:flex-col md:items-center md:space-x-4 mt-2 relative">
     <label className="sourcelabel block text-lg font-medium text-slate-700 md:w-24  mt-5">From:</label>
     <input 
    type="text"
    name="source"
    value={fromTransportLocation.source}
    onChange={handlechange}
 
    placeholder='Enter your loading city'
    className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
   required

  />
  {fromLocationSuggestions.length >0 &&(
     <ul className="autocomplete-list absolute left-0 right-0 top-[90px] z-20 bg-white border border-gray-300 rounded shadow-lg max-h-60 overflow-auto scrollbar-hide">
     {fromLocationSuggestions.map((suggestion) => (
         <li
             key={suggestion}
             className="autocomplete-item flex flex-col lg:h-10 h-12 py-2 px-2 border rounded shadow-md"
             onClick={() => handleSuggestionClick('source', suggestion)}
         >
             {suggestion}
         </li>
     ))}
 </ul>
  )}
    {errors.source && <p className="text-red-500 text-md mt-1">{errors.source}</p>}
</div>
</div>

  {/* <!-- Destination Input --> */}
  <div >
  <div className="flex flex-col space-y-1 md:flex-col md:items-center md:space-x-4 mt-12 relative">
  <label className="sourcelabel block text-lg font-medium text-slate-700 md:w-24 ">To:</label>
  <input 
    type="text"
    name="destination"
    value={fromTransportLocation.destination}
    onChange={handlechange}
    placeholder='Enter your unloading city'
    className="md:w-80 w-auto  px-3 py-2 border rounded-md bg-gray-50 text-slate-500 border-slate-300 focus:border-sky-500 focus:ring-sky-500 focus:outline-none disabled:opacity-75"
   required
  />
   {toLocationSuggestions.length > 0 && (
    <div>
                    <ul className="autocomplete-list absolute left-2 right-0 top-[70px] z-20 bg-white border border-gray-300 rounded shadow-lg max-h-60 overflow-auto">
                        {toLocationSuggestions.map((suggestion) => (
                            <li
                                key={suggestion}
                                className=" autocomplete-item flex flex-col  lg:h-10 h-12 py-2 px-2 border rounded shadow-md "
                                 onClick={() => handleSuggestionClick('destination', suggestion)}
                            >
                                {suggestion}
                            </li>
                        ))}
                    </ul>
                    </div>
                )}
               {errors.destination && <p className="text-red-500 text-md mt-1">{errors.destination}</p>}
              <div className="mt-auto pt-6">
               <button
                type="submit"
                className="w-80 mt-48 bg-blue-600 text-white px-4 py-3 rounded-md hover:bg-blue-700 transition-colors font-medium"
                >
              Continue
            </button>
          </div>

</div>
</div>
  
    </form>
    </div>
    </div>                                   
  )
}

export default TransportDestinations
