import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Vehicletype.css';


function Vehicletype({ data, setData,onNext }) {
  const [selectedType, setSelectedType] = useState("Truck");
  const [weight, setWeight] = useState('');
  const [weightUnit, setWeightUnit] = useState("Ton");
  const [truckLengthOption, setTruckLengthOption] = useState([]);
  const [Length, setLength] = useState('');
  const [height, setHeight] = useState('');
  const [error, setError] = useState(null);
  const [selectedSmallTruck, setSelectedSmallTruck] = useState("");
  const [showLength, setShowlength] = useState(false);
  const [showWeight, setShowweight] = useState(false);
 
  const handleTypeChange = (type) => {
    setSelectedType(type);
    setWeight('');
    setHeight('');
    setLength('');
    setWeightUnit('Ton');
    setSelectedSmallTruck('');
  };
 

  const [errors, setErrors] = useState({
      weight: "",
      length: "",
      height: "",
      general: "",
  });
  
  const fetchtrucklengthoptions = async () => {
    try {
      setErrors({});
      const token = localStorage.getItem('jwtToken'); 

      const response = await axios.post('http://localhost:8080/get-truck-length-options', {
        
        vehicleType: selectedType,
        weight: weight,
        weightUnit: weightUnit
      },{
        
          headers: {
           "Authorization": `Bearer ${token}` ,// ⬅️ send token in header
             "Content-Type": "application/json"
          }
        
      });
      console.log(response.data);
      setTruckLengthOption(response.data);

    } catch (error) {
      console.log("Error:", error);
      setError("Failed to load truck options. Please try again.");
    }
  };

  useEffect(() => {
    if (weight && (selectedType === "Truck" || selectedType === "Container")) {
      fetchtrucklengthoptions();
    }
  }, [selectedType, weight, weightUnit]);



  useEffect(() => {
    if (data) {
      setSelectedType(data.transportType || "Truck");
      setWeight(data.weight || "");
      setWeightUnit(data.weightUnit || "Ton");
      setLength(data.length || "");
      setHeight(data.height || "");
      setSelectedSmallTruck(data.small_truck_details || "");
      
      // Set UI state based on existing data
      if (data.weight) {
        setShowlength(true)
      }
      if (data.length) {
        setShowweight(true)
      }
    }
  }, [data])


  const handlesubmit = async (e) => {
    e.preventDefault();
    let isValid = true
    const newErrors = { weight: "", length: "", height: "",general:"" }

    if ((selectedType === "Truck" || selectedType === "Container") && !height.trim()) {
      newErrors.height = "height is required";
      isValid = false;
    }
    if ((selectedType === "Truck" || selectedType === "Container") && !weight.toString().trim()) {
      newErrors.weight = "weight is required"
      isValid = false
    }
    if((selectedType === "Truck" || selectedType === "Container") && !Length.trim()){
         newErrors.length= "length is required"
    }
     if (selectedType === "Smallcart") {
      if (!selectedSmallTruck) {
        newErrors.general = "Please select a small pickup truck";
        isValid = false;
      }
    }
    if (!isValid) {
      setErrors(newErrors)
      return
    }

    try {
      let smallTruckWeight = 0;
      if (selectedType === "Smallcart" && selectedSmallTruck) {
        const weightPart = selectedSmallTruck.split("|")[1].trim();
        if (weightPart) {
          smallTruckWeight = Number.parseInt(weightPart.split(" ")[0]) || 0;
        }
      }
      
      const newData = {
          transportType: selectedType,  
          weight: selectedType === "Smallcart" ? smallTruckWeight : weight,
          weightUnit: selectedType === "Smallcart" ? "Kg" : weightUnit,
          length: selectedType === "Smallcart" ? selectedSmallTruck.split("|")[0].trim() : Length,
          height: selectedType === "Smallcart" ? "7ft" : height,
          small_truck_details: selectedType === "Smallcart" ? selectedSmallTruck : "Ace,Bolero",
      };
      if (setData) {
        setData((prev) => ({
          ...prev,
          ...newData,
        }))
      }
      onNext(newData);

    } catch (error) {
      console.error("Error submitting form:", error);
      if (error.response) {
        console.log(error.response);
        setErrors((prev) => ({
          ...prev,
          general: "An error occurred. Please try again later.",
        }))
      }
      
    }
  }

  return (
    <div>
      <div className="flex justify-center items-center">
        <form className="h-[600px] w-[500px] flex flex-col mb-3 pr-20 bg-white p-6 sm:p-8 rounded-lg shadow-lg max-w-sm" onSubmit={handlesubmit}>
          <h3 className="text-2xl md:text-3xl font-semibold mb-6">Vehicle Type</h3>
          {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">{error}</div>}
          {/* Vehicle Type Selection */}
          <div className=" flex flex-row space-y-3 sm:space-y-0 lg:flex-row sm:space-x-3 mb-6 items-center overflow-x-auto whitespace-nowrap scrollbar-thin">
            <button type="button" onClick={() => handleTypeChange("Truck")} className={`form-vehicle-box w-48 h-10 px-2 py-2 lg:rounded-md rounded-md text-sm md:text-base transition-colors ${selectedType === "Truck" ? "bg-blue-50 text-blue-800 border-2 border-blue-300" : "bg-gray-100 text-gray-800 border border-gray-300 hover:bg-gray-200"}`}>
              Open Truck
            </button>
            <button type="button" onClick={() => handleTypeChange("Container")} className={`form-vehicle-box px-2 py-2 w-48 h-10 rounded-md text-sm md:text-base transition-colors ${selectedType === "Container" ? "bg-blue-50 text-blue-800 border-2 border-blue-300" : "bg-gray-100 text-gray-800 border border-gray-300 hover:bg-gray-200"}`}>
              Container
            </button>
            <button type="button" onClick={() => handleTypeChange("Smallcart")} className={`form-vehicle-box px-4 py-2 h-10  rounded-md text-sm md:text-base transition-colors ${selectedType === "Smallcart" ? "bg-blue-50 text-blue-800 border-2 border-blue-300" : "bg-gray-100 text-gray-800 border border-gray-300 hover:bg-gray-200"}`}>
              Small Pickup Truck
            </button>
          </div>

          {/* Truck or Container Options */}
          {(selectedType === "Truck" || selectedType === "Container") && (
            <div className="space-y-6">
              {/* Weight Selection */}
              <div className="space-y-2">
                <label className="block text-sm font-medium text-gray-700">Material Weight</label>
                <div className="flex flex-col sm:flex-row space-y-2 sm:space-y-0 sm:space-x-2">
                  <input type="number" 
                  value={weight} 
                  placeholder="Enter Weight"
                  required
                   className="flex-grow px-3 py-2 border rounded-md bg-gray-50 text-gray-700 border-gray-300 focus:border-blue-500 focus:ring-1 focus:ring-blue-500 focus:outline-none"
                    onChange={(e) => {
                    const val = e.target.value;
                    setWeight(val);
                  // Clear any previous errors for this input

                    setErrors((prev) => ({ ...prev, [weight]: "" }))
                    if (val.trim() !== '') {
                      setShowlength(true);
                    } else {
                      setShowlength(false);
                    }
                    
                  }} />
                  <div className="flex space-x-2">
                    <button type="button" onClick={() => setWeightUnit("Ton")} className={`px-4 py-2 rounded-md transition-colors ${weightUnit === "Ton" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                      Ton
                    </button>
                    <button type="button" onClick={() => setWeightUnit("Kg")} className={`px-4 py-2 rounded-md transition-colors ${weightUnit === "Kg" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                      Kg
                    </button>
                  </div>
                </div>
                {errors.weight && <p className="text-red-500 text-md mt-1 ml-2">{errors.weight}</p>}
              </div>


              {/* Truck Length Options */}
              {showLength && truckLengthOption.length > 0 && (
                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700">Truck Length</label>
                  <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-2">
                    {truckLengthOption.map((option) => (
                      <button type="button" key={option} onClick={() => {
                        setLength(option);
                        setShowweight(true);
                        // Clear any previous errors for this input
                        setErrors((prev) => ({ ...prev, length: "" }));
                      }} className={`px-3 py-2 rounded-md text-sm transition-colors ${Length === option ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                        {option}
                      </button>
                    ))}
                  </div>
                  {errors.length && <p className="text-red-500 text-md mt-1">{errors.length}</p>}
                </div>
              )}


              {/* Truck Height */}
              {showWeight && (
                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700">Truck Height</label>
                  <div className="flex flex-wrap gap-2">
                    <button type="button" 
                    onClick={() => {
                      setHeight("8.0ft");
                      setErrors((prev) => ({ ...prev, height: "" }));
                    }
                      
                    

                    }
                     className={`px-4 py-2 rounded-md transition-colors ${height === "8.0ft" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                      8.0ft
                    </button>
                  </div>
                  {errors.height && <p className="text-red-500 text-md mt-1">{errors.height}</p>}
                </div>
              )}
            </div>
          )}
          {/* Small Cart Options */}
          {selectedType === "Smallcart" && (
            <div className="space-y-4">
              <label className="block text-sm font-medium text-gray-700">Select Pickup Truck</label>
              <div className="flex flex-col gap-3">
                <button type="button" onClick={() => setSelectedSmallTruck("7ft Tata Ace | 1250 Kg")} className={`px-4 py-3 w-56 rounded-md text-sm transition-colors ${selectedSmallTruck === "7ft Tata Ace | 1250 Kg" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                  7ft Tata Ace | 1250 Kg
                </button>
                <button type="button" onClick={() => setSelectedSmallTruck("8ft Bolero Pickup | 1500 Kg")} className={`px-4 py-3 w-56 rounded-md text-sm transition-colors ${selectedSmallTruck === "8ft Bolero Pickup | 1500 Kg" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                  8ft Bolero Pickup | 1500 Kg
                </button>
                <button type="button" onClick={() => setSelectedSmallTruck("14ft Canter | 3500 Kg")} className={`px-4 py-3 w-56 rounded-md text-sm transition-colors ${selectedSmallTruck === "14ft Canter | 3500 Kg" ? "bg-blue-500 text-white" : "bg-gray-200 text-gray-800 hover:bg-gray-300"}`}>
                  14ft Canter | 3500 Kg
                </button>
              </div>
            </div>
          )}
         <div className="mt-auto pt-6">
               <button
                type="submit"
                className="w-full bg-blue-600 text-white px-4 py-3 rounded-md hover:bg-blue-700 transition-colors font-medium"
                >
              Continue
            </button>
          </div>
          
        </form>
      </div>
    </div>
  );
}

export default Vehicletype
