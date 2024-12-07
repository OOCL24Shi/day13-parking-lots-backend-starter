<span style="color:#89A8B2; font-weight:bold;">Prompt 1</span>

> Please go through the code and explain your understanding of it.

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 2</span>

> I'll be sharing some background information first, and then I'll provide you with the new requirements.

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 3</span>

> **Problem Statement**
>
> **Background**:
>
> As a **Parking Manager**, I am responsible for managing three parking lots:
>
> ● **The Plaza Park** (9 parking capacity)
>
> ● **City Mall Garage** (12 parking capacity)
>
> ● **Office Tower Parking** (9 parking capacity)
>
> I have employed three **Parking Boys** to help manage these parking lots, each utilizing a specific parking strategy:
>
> 1. **Standard** parking strategy
>
> 2. **Smart** parking strategy
>
> 3. **Super Smart** parking strategy
>
> To optimize management and streamline operations, I need an application that assists me in visualizing and efficiently managing 
>
> the car parking and retrieval process, while also keeping track of the current usage of each parking lot.

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 4</span>

> Implement the ParkingManager, which holds:
>
> 1.3 parking lots.
> 2.3 parking boys, each with a different parking strategy.
>
> It should have a method:
>
> park: This method takes the following inputs:
> strategy:"STANDARD"|"SMART"|"SUPER".
> plateNumber:a string representing the license plate number of car.
>
> Based on the selected strategy, it will apply one of the following parking strategies:
>
> SequentiallyStrategy
> MaxAvailableStrategy
> AvailableRateStrategy

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 5</span>

> Please create test cases for the ParkingManager that follow these guidelines:  
> 1.Use the "Given-When-Then" structure. 
> 2.Name each test case in the format: should_action_when_condition_given_setup_, using underscores to separate the words. 
> 3.Use parameterized-tests when needed 
>
> Business Background:  
> 1.The standard parking boy isn’t very clever and always parks cars sequentially. If the first parking lot is full, the standard parking boy will park the car in the second parking lot.  
> 2.The smart parking boy always parks cars in the parking lot with more empty spots.  
> 3.The super parking boy parks cars in the parking lot with the largest available position rate (available positions / total capacity).  
> 4.If all parking lots have the same number of available spots or the same availability, the car should be parked in the first lot, then the second, and then the third.  
>
> Test cases such as SuperParkingBoyTest.java, StandardParkingBoyTest.java, and SmartParkingBoyTest.java serve as good examples.

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 6</span>

> I’ll begin adding requirements shortly. Please follow the standard software development procedures, which include:
>
> 1.Tasking
> 2.Test-Driven Development (TDD)
> 3.Implementation

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 6</span>

> Here comes the 1st additional requirement:
>
> Parking Lot Status Display
> ○ The system should clearly display the current status of all three parking lots, showing the license plate of the car parked 
> in each parking position to provide a real-time view of parking lot usage
>
> Please follow the standard software development procedures, which include:
>
> 1.Tasking
> 2.Test-Driven Development (TDD)
> 3.Implementation

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 7</span>

> is it better to put getParkingLotStatus in the ParkingLot class?

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 8</span>

> then redo the tasking, tdd and implementation

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 9</span>

> Next requirement:**Car Parking and Fetching**
>
> ○ Enable the input of a user’s license plate number to perform car parking or fetching.
>
> ○ During parking, the license plate number should be correctly assigned to an available parking position and reflected in the parking lot’s status display.
>
> ○ During retrieval, the system should accurately release the corresponding parking slot and update the parking lot’s status. Please follow the standard software development procedures, which include:
>
> 1.Tasking 2.Test-Driven Development (TDD) 3.Implementation

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 10</span>

> The requirement is to park and fetch cars using the license plate number, not to fetch by ticket, while your codes seem to fetch the cars by tickets? please help update the codes

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 11</span>

> next requirement:  License Plate Validation ○ License plates must follow the format standard: two letters + four digits (e.g., “AB-1234”). ○ The system must reject empty or invalid license plate entries

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 12</span>

> For the test cases, please include tests for ParkingLot and ParkingManager using both valid and invalid plate numbers.

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 13</span>

> I want to add a controller layer to handle parking and fetching, as well as a service layer. The goal is to build a backend that allows the front end to pass a license plate and a parking strategy (SUPER, STANDARD, or SMART) to park and fetch cars. Additionally, it should display a matrix of license plate numbers separated by parking lots, with the parking lot name displayed below each matrix. Can you guide me on the steps to update the backend code?

<hr>

<span style="color:#89A8B2; font-weight:bold;">Prompt 14</span>

> create test cases for ParkingController and ParkingService

<hr>

To check the **Prompts** for **font-end codes**, please refer to **README.md** in repository **day13-parking-lots-frontend-starter**

