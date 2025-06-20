google.charts.load('current', {'packages':['timeline', 'corechart']});
google.charts.setOnLoadCallback(function() {
	initTimelineChart(); // 圖1
	drawFoodChart();	// 圖3
	});

	// 圖表 1 
	function initTimelineChart() {
		  loadTimelineData(); // 載入當日
	}
	
	function loadTimelineData() {
		  const discussId = document.getElementById("timeline-chart").dataset.discussId;
		  
		  fetch(`/ornibase/discuss/chart-timeline?discussId=${discussId}`)
		    .then(res => res.json())
		    .then(data => drawTimeline(data))
		    .catch(err => console.error("圖表載入失敗", err));
		}
	
	function drawTimeline(data) {
		  const container = document.getElementById('timeline-chart');
		  const chart = new google.visualization.Timeline(container);
		  const dataTable = new google.visualization.DataTable();

		  dataTable.addColumn({ type: 'string', id: 'Subject' });
		  dataTable.addColumn({ type: 'string', id: 'Action' });
		  dataTable.addColumn({ type: 'datetime', id: 'Start' });
		  dataTable.addColumn({ type: 'datetime', id: 'End' });

		  
		  data.forEach(row => {
		    dataTable.addRow([
		      row.subject,
		      row.action,
		      new Date(`1970-01-01T${row.startTime}`),
		      new Date(`1970-01-01T${row.endTime}`),
		    ]);
		  });

			
		  const chartOptions = {
		    timeline: {
		      showRowLabels: true,
		    },
		    avoidOverlappingGridLines: false,
			hAxis: {
			  minValue: new Date("1970-01-01T00:00:00"),
			  maxValue: new Date("1970-01-01T23:59:59")
			}
		  };

		  chart.draw(dataTable, chartOptions);
		}
		
	// 圖表 2	
	function drawFoodChart() {
	  const discussId = document.getElementById("food-chart").dataset.discussId;

	  fetch(`/ornibase/discuss/chart-food-count?discussId=${discussId}`)
	    .then(res => res.json())
	    .then(data => {
	      const dataArray = [['食物種類', '數量']];
	      for (const [food, count] of Object.entries(data)) {
	        dataArray.push([food, count]);
	      }

	      const dataTable = google.visualization.arrayToDataTable(dataArray);

	      const options = {
			colors: ['#e74c3c'], 
	        legend: { position: 'none' },
	        hAxis: {
	          title: '出現次數'
	        },
	      };

	      const chart = new google.visualization.BarChart(document.getElementById('food-chart'));
	      chart.draw(dataTable, options);
	    })
	    .catch(err => console.error("食物圖表載入失敗", err));
	}