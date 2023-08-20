let csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

let checkboxes = document.getElementsByName("checkbox");
let taskTitles = document.querySelectorAll(".item a");
let buttons = document.getElementsByName("button");

checkboxes.forEach(function (checkbox, index) {
    checkbox.addEventListener("change", function () {
      
      // Send the PUT request when the checkbox is clicked
      updateTaskStatus(index, checkbox.checked);
    });
  });


  function updateTaskStatus(index, completed) {
    var href = taskTitles[index].getAttribute("href");
    var taskId = href.substring(href.lastIndexOf('=') + 1);

	fetch('/tasks/check/' + taskId, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken  
      },
      body: JSON.stringify({ completed: completed })
    })
      .then(function (response) {
        if (response.ok) {
          console.log('Task status updated successfully');
		  location.reload();
          
        } else {
          console.error('Failed to update task status');
        }
      })
      .catch(function (error) {
        console.error('Error:', error);
      });
  }

function deleteTask(taskId) {
    fetch('/tasks/' + taskId, {
      method: 'DELETE',
	  headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken  // Include CSRF token in the request header
      }
    })
    .then(function(response) {
      if (response.ok) {
        console.log('Task deleted successfully');
		location.reload();
      } else {
        console.log('Error deleting task');
      }
    })
    .catch(function(error) {
      console.error('Error:', error);
    });
  }