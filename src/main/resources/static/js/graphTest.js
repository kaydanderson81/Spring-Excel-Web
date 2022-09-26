// extracts the labels, the employee names
console.log(employees);
const labels = employees.reduce(function(result, item) {
  result.push(item.name);
  return result;
}, []);
// colors generator
const randomColorGenerator = function () {
  return '#' + (Math.random().toString(16) + '0000000').slice(2, 8);
};
// extracts all projects creating an object every every key is project name
// and prepares the datasets for chart
const projects = employees.reduce(function(result, item) {
  item.projects.forEach(function(prj){
    const prjName = prj.projectName;
    if (!result[prjName]) {
      result[prjName] = {
        label: prj.projectName,
        data: [],
        backgroundColor: randomColorGenerator()
      };
    }
  });
  return result;
}, {});
// adds data to the project
employees.forEach(function(item) {
  for (const prjName of Object.keys(projects)) {
    const prj = projects[prjName];
    const empPrj = item.projects.filter(el => el.projectName === prjName);
    if (empPrj.length) {
      prj.data.push(empPrj[0].personMonths);
    } else {
      prj.data.push(0);
    }
  }
});

console.log(employees);

const ctx = document.getElementById("myChart");
const myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: labels,
    datasets: Object.values(projects)
  },
  options: {
    scales: {
      x: {
        stacked: true
      },
      y: {
        stacked: true
      }
    }
  }
});
