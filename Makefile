.PHONY: help build clean install test sonar export-issues rebuild all

PROJECT_NAME ?= ublkit-java
PERSEO_ROOT ?= $(shell git rev-parse --show-toplevel 2>/dev/null || echo ../..)

help:
	@echo "╔════════════════════════════════════════════════╗"
	@echo "║  $(PROJECT_NAME) - Build Targets              ║"
	@echo "╚════════════════════════════════════════════════╝"

build:
	@echo "📦 Building $(PROJECT_NAME)..."; mvn -q clean compile 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Success"

test:
	@echo "🧪 Testing..."; mvn -q test 2>&1 | grep -E "BUILD|ERROR|Tests" | tail -5 || echo "✅ Passed"

test-verbose:
	@mvn test

clean:
	@echo "🧹 Cleaning..."; mvn -q clean; echo "✅ Done"

install:
	@echo "📥 Installing..."; mvn -q install -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Done"

package:
	@echo "📦 Packaging..."; mvn -q package -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Done"

rebuild: clean build

all: install build test
	@echo "🎉 Complete"

sonar:
	@echo "🔍 SonarQube..."; mvn -q sonar:sonar 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Done"

export-issues:
	@mkdir -p $(PERSEO_ROOT)/reports; mvn -q dependency:tree > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-dependencies.txt 2>&1; echo "✅ Done"
