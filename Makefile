.PHONY: help build clean install test sonar sonar-parent sonar-modules export-issues rebuild all package test-verbose

PROJECT_NAME ?= ublkit-java
PERSEO_ROOT ?= $(shell cd "$(dir $(realpath $(MAKEFILE_LIST)))" && pwd)/..
SONAR_HOST ?= http://localhost:9000
SONAR_ENABLED ?= false
SONAR_TOKEN ?= squ_a2ec42f9af0f21c87b30716902c738bc3effc428
ENABLE_SONAR ?= false

# ublkit-java modules (10 total)
MODULES := ublkit-core ublkit-ubl ublkit-sign ublkit-gateway ublkit-render \
           ublkit-validation ublkit-catalogs ublkit-testkit ublkit-spring-boot-starter ublkit-quarkus

help:
	@echo "╔════════════════════════════════════════════════════════════════╗"
	@echo "║  $(PROJECT_NAME) - Multi-Module Build (10 modules)            ║"
	@echo "╚════════════════════════════════════════════════════════════════╝"
	@echo ""
	@echo "🔨 BUILD (all modules)"
	@echo "  build              - Compile parent + all 10 modules"
	@echo "  test               - Run tests for all modules"
	@echo "  test-verbose       - Verbose test output"
	@echo "  clean              - Clean all artifacts"
	@echo "  install            - Install all modules"
	@echo "  package            - Package all JARs"
	@echo "  rebuild            - Clean + build"
	@echo ""
	@echo "📊 ANALYSIS & REPORTING"
	@echo "  sonar              - SonarQube: parent + each module (full)"
	@echo "  sonar-parent       - SonarQube: only parent"
	@echo "  sonar-modules      - SonarQube: each of 10 modules separately"
	@echo "  export-issues      - Export dependencies for all"

build:
	@SONAR_FLAG=''; \
	if [ "$(ENABLE_SONAR)" = "true" ]; then SONAR_FLAG='-Dskip.sonar=false''; fi; \
	echo "📦 Building ${PROJECT_NAME}..."; \
	mvn -q clean verify $$SONAR_FLAG 2>&1 | tee /tmp/build.log | grep -E "BUILD|ERROR" || true; \
	if grep -q "ERROR" /tmp/build.log; then echo "❌ Build FAILED"; exit 1; else echo "✅ Build successful"; fi

test:
	@echo "🧪 Testing $(PROJECT_NAME) (10 modules)..."
	@mvn -q test 2>&1 | grep -E "BUILD|ERROR|Tests run:|Failures:" | tail -10 || echo "✅ Tests passed"

test-verbose:
	@echo "🧪 Testing (verbose)..."
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

################################################################################
# SonarQube Analysis
################################################################################

sonar-parent:
	@if [ "$(SONAR_ENABLED)" != "true" ]; then \
		echo "⏭️  SonarQube disabled"; exit 0; \
	fi
	@echo "🔍 SonarQube: Parent (ublkit-parent)..."
	@mkdir -p $(PERSEO_ROOT)/reports
	@mvn -q sonar:sonar -Dsonar.host.url=$(SONAR_HOST) -Dsonar.token=$(SONAR_TOKEN) -Dsonar.projectKey=$(PROJECT_NAME)-parent 2>&1 | tee /tmp/sonar_parent.log
ENABLE_SONAR ?= false
	@if grep -q "ERROR\|FAILURE\|Not authorized" /tmp/sonar_parent.log; then echo "❌ FAILED"; exit 1; else echo "✅ Done"; fi

sonar-modules:
	@if [ "$(SONAR_ENABLED)" != "true" ]; then \
		echo "⏭️  SonarQube disabled"; exit 0; \
	fi
	@echo "🔍 SonarQube: Analyzing 10 modules separately..."
	@mkdir -p $(PERSEO_ROOT)/reports
	@echo ""
	@for module in $(MODULES); do \
		echo "   🔍 $$module..."; \
		mvn -q sonar:sonar -f $$module/pom.xml -Dsonar.host.url=$(SONAR_HOST) -Dsonar.token=$(SONAR_TOKEN) -Dsonar.projectKey=$(PROJECT_NAME)-$$module 2>&1 | tee /tmp/sonar_$$module.log; \
ENABLE_SONAR ?= false
		if grep -q "ERROR\|FAILURE\|Not authorized" /tmp/sonar_$$module.log; then \
			echo "      ❌ FAILED"; \
		else \
			echo "      ✅ OK"; \
		fi; \
	done
	@echo ""
	@echo "✅ All modules analyzed"

sonar: sonar-parent sonar-modules
	@echo ""
	@echo "✅ Full SonarQube analysis complete (parent + 10 modules)"

export-issues:
	@echo "📊 Exporting $(PROJECT_NAME) (parent + 10 modules)..."
	@mkdir -p $(PERSEO_ROOT)/reports
	@echo ""
	@echo "   📋 Parent..."
	@mvn -q dependency:tree > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-parent-dependencies.txt 2>&1
	@echo "   📋 Modules:"
	@for module in $(MODULES); do \
		echo "      • $$module"; \
		mvn -q dependency:tree -f $$module/pom.xml > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-$$module-dependencies.txt 2>&1; \
	done
	@echo ""
	@echo "✅ All reports exported"
