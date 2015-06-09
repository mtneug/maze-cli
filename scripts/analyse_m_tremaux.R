# This script analyses the statistical output of the m maze generator algorithm
# and a solver.

# clean start
rm(list = ls())

################################################################################
# settings

input.path       = "build/data/solver-statistics.csv"

output.default   = pdf
output.width     = 12
output.height    = 7
output.path.base = "build/data/"

################################################################################
# functions
output.plot.m <- function(output.path.file.name, plot.first) {
  output.default(
    paste0(output.path.base, output.path.file.name),
    width  = output.width,
    height = output.height
  )

  plot.first()

  abline(v = 34, lty = 2, col = "gray40")
  abline(v = 67, lty = 2, col = "gray40")

  axis(1, 34, 34)
  axis(1, 67, 67)

  dev.off()
}

################################################################################
# read data
data <- read.csv(
  file      = input.path,
  header    = FALSE,
  col.names = c(
    # maze
    "maze.width",
    "maze.height",
    "maze.cells",
    # maze generator
    "maze.generator.name",
    "maze.cells.passable",
    "maze.cells.nonpassable",
    "maze.difficulty",
    "maze.difficulty.level",
    # maze solution
    "maze.solutions",
    "maze.solution.length",
    # maze solver
    "maze.solver.name",
    "maze.solver.steps",
    "maze.solver.places",
    "maze.solver.places.inSolution",
    "maze.solver.places.visited",
    "maze.solver.deadends",
    "maze.solver.deadends.length.mean"
  )
)

################################################################################
# Percentage of passable cells

data$maze.cells.passable.percent = data$maze.cells.passable / data$maze.cells

output.plot.m("percentage_passable_cells.pdf", function() {
  plot(
    aggregate(data$maze.cells.passable.percent, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "% passable cells",
    col  = "red",
    main = "Percentage of passable cells"
  )

  lines(aggregate(data$maze.cells.passable.percent, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.cells.passable.percent, list(data$maze.difficulty), min), col = "red")

  lines(0:100, seq(min(data$maze.cells.passable.percent), 1, length.out = 101), lty = 2, col = "gray40")
})

################################################################################
# Length of the solution

output.plot.m("solution_length.pdf", function() {
  plot(
    aggregate(data$maze.solution.length, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "length",
    col  = "red",
    main = "Length of the solution"
  )

  lines(aggregate(data$maze.solution.length, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solution.length, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Maximum length of the solution

output.plot.m("solution_length_max.pdf", function() {
  plot(
    aggregate(data$maze.solution.length, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "length",
    col  = "red",
    main = "Maximum length of the solution"
  )
})

################################################################################
# Number of steps the Trémaux's algorithm needed

output.plot.m("steps_tremaux_needed.pdf", function() {
  plot(
    aggregate(data$maze.solver.steps, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "steps",
    col  = "red",
    main = "Number of steps the Trémaux's algorithm needed"
  )

  lines(aggregate(data$maze.solver.steps, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.steps, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Maximum number of steps the Trémaux's algorithm needed

output.plot.m("steps_tremaux_needed_max.pdf", function() {
  plot(
    aggregate(data$maze.solver.steps, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "steps",
    col  = "red",
    main = "Maximum number of steps the Trémaux's algorithm needed"
  )
})

################################################################################
# Number of places encountered by the Trémaux's algorithm

output.plot.m("places_encountered_by_tremaux.pdf", function() {
  plot(
    aggregate(data$maze.solver.places, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "places",
    col  = "red",
    main = "Number of places encountered by the Trémaux's algorithm"
  )

  lines(aggregate(data$maze.solver.places, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.places, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Number of place visits by the Trémaux's algorithm

output.plot.m("place_visits_by_tremaux.pdf", function() {
  plot(
    aggregate(data$maze.solver.places.visited, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "places",
    col  = "red",
    main = "Number of place visits by the Trémaux's algorithm"
  )

  lines(aggregate(data$maze.solver.places.visited, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.places.visited, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Percent of encountered places part of the solution

data$maze.solver.places.inSolution.percent <- data$maze.solver.places.inSolution / data$maze.solver.places

output.plot.m("places_encountered_part_of_solution.pdf", function() {
  plot(
    aggregate(data$maze.solver.places.inSolution.percent, list(data$maze.difficulty), min),
    type = "l",
    xlab = "difficulty",
    ylab = "% encountered places part of solution",
    col  = "red",
    main = "Percent of encountered places part of the solution"
  )

  lines(aggregate(data$maze.solver.places.inSolution.percent, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.places.inSolution.percent, list(data$maze.difficulty), max), col = "red")
})

################################################################################
# Ratio of place visits and total places

data$maze.solver.places.visited.ratio <- data$maze.solver.places.visited / data$maze.solver.places

output.plot.m("place_visits_places_ratio.pdf", function() {
  plot(
    aggregate(data$maze.solver.places.visited.ratio, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "place visits / total places",
    col  = "red",
    main = "Ratio of place visits and total places"
  )

  lines(aggregate(data$maze.solver.places.visited.ratio, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.places.visited.ratio, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Number of dead ends encountered by the Trémaux's algorithm

output.plot.m("deadends_encountered_by_tremaux.pdf", function() {
  plot(
    aggregate(data$maze.solver.deadends, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "dead ends",
    col  = "red",
    main = "Number of dead ends encountered by the Trémaux's algorithm"
  )

  lines(aggregate(data$maze.solver.deadends, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.deadends, list(data$maze.difficulty), min), col = "red")
})

################################################################################
# Mean length of dead ends encountered by the Trémaux's algorithm

output.plot.m("length_deadends_encountered_by_tremaux.pdf", function() {
  plot(
    aggregate(data$maze.solver.deadends.length.mean, list(data$maze.difficulty), max),
    type = "l",
    xlab = "difficulty",
    ylab = "length",
    col  = "red",
    main = "Mean length of dead ends encountered by the Trémaux's algorithm"
  )

  lines(aggregate(data$maze.solver.deadends.length.mean, list(data$maze.difficulty), mean), col = "red")
  lines(aggregate(data$maze.solver.deadends.length.mean, list(data$maze.difficulty), min), col = "red")
})
