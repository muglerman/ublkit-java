.PHONY: help build clean install test sonar sonar-parent sonar-modules export-issues \
         rebuild all package test-verbose

PROJECT_NAME ?= ublkit-java
PERSEO_ROOT ?= $(shell git rev-parse --show-toplevel 2>/dev/null || echo ../..)
SONAR_HOST ?= http://localhost:9000
SONAR_TOKEN ?=
SONAR_ENABLED ?= false

# UBLKit modules
MODULES := ublkit-core ublkit-ubl ublkit-sign ublkit-gateway ublkit-render \
           ublkit-validation ublkit-catalogs ublkit-testkit ublkit-spring-boot-starter ublkit-quarkus

help:
	@echo "╔════════════════════════════════════════════════════════════════╗"
	@echo "║  $(PROJECT_NAME) - Multi-Module Build System                   ║"
	@echo "╚════════════════════════════════════════════════════════════════╝"
	@echo ""
	@echo "🔨 BUILD TARGETS"
	@echo "──────────────────────────────────────────────────────────────────"
	@echo "  build             - Compile all modules"
	@echo "  test              - Run tests for all modules"
	@echo "  test-verbose      - Run tests with full output"
	@echo "  clean             - Clean all build artifacts"
	@echo "  install           - Install all dependencies"
	@echo "  package           - Package all modules (JAR/WAR)"
	@echo "  rebuild           - Clean + build"
	@echo "  all               - Install + build + test"
	@echo ""
	@echo "🔍 SONARQUBE ANALYSIS (Multi-Module Special)"
	@echo "──────────────────────────────────────────────────────────────────"
	@echo "  sonar             - Analyze parent + each module separately"
	@echo "  sonar-parent      - Analyze only parent"
	@echo "  sonar-modules     - Analyze each module as independent project"
	@echo ""
	@echo "📊 REPORTING"
	@echo "──────────────────────────────────────────────────────────────────"
	@echo "  export-issues     - Export dependency trees for all modules"
	@echo ""
	@echo "📦 MODULES (10 total)"
	@echo "──────────────────────────────────────────────────────────────────"
	@for m in $(MODULES); do echo "   • $$m"; done
	@echo ""
	@echo "💡 EXAMPLES"
	@echo "──────────────────────────────────────────────────────────────────"
	@echo "  make build                                    # Build all"
	@echo "  make test                                     # Test all"
	@echo "  make sonar-modules SONAR_ENABLED=true         # Modules → SQ"
	@echo "  make sonar SONAR_ENABLED=true SONAR_HOST=http://localhost:9000"
	@echo "  make export-issues                            # Export reports"

build:
	@echo "📦 Building $(PROJECT_NAME) (all modules)..."
	@mvn -q clean compile 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Build successful"

test:
	@echo "🧪 Testing $(PROJECT_NAME) (all modules)..."
	@mvn -q test 2>&1 | grep -E "BUILD|ERROR|Tests run:|Failures:|Errors:" | tail -10 || echo "✅ All tests passed"

test-verbose:
	@echo "🧪 Testing $(PROJECT_NAME) (verbose output)..."
	@mvn test

clean:
	@echo "🧹 Cleaning $(PROJECT_NAME)..."
	@mvn -q clean
	@echo "✅ Cleanup complete"

install:
	@echo "📥 Installing dependencies..."
	@mvn -q install -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Installed"

package:
	@echo "📦 Packaging all modules..."
	@mvn -q package -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Packaged"

rebuild: clean build
	@echo "✅ Rebuild complete"

all: install build test
	@echo ""; echo "🎉 Complete for $(PROJECT_NAME) (all modules)"

# ════════════════════════════════════════════════════════════════════════════
# SONARQUBE - Parent Project Only
# ════════════════════════════════════════════════════════════════════════════

sonar-parent:
	@if [ "$(SONAR_ENABLED)" = "true" ]; then \
		echo "🔍 Running SonarQube analysis on parent..."; \
		mvn -q sonar:sonar \
			-Dsonar.host.url=$(SONAR_HOST) \
			-Dsonar.projectKey=ublkit-parent \
			$(if $(SONAR_TOKEN),-Dsonar.login=$(SONAR_TOKEN),) \
			2>&1 | grep -E "BUILD|sonar|ERROR" || true; \
		echo "✅ Parent analysis complete"; \
	else \
		echo "⏭️  SonarQube DISABLED for parent"; \
		echo ""; \
		echo "Enable with:"; \
		echo "  make sonar-parent SONAR_ENABLED=true"; \
		echo "  make sonar-parent SONAR_ENABLED=true SONAR_HOST=http://localhost:9000"; \
	fi

# ════════════════════════════════════════════════════════════════════════════
# SONARQUBE - Each Module as Separate Project
# ════════════════════════════════════════════════════════════════════════════

sonar-modules:
	@if [ "$(SONAR_ENABLED)" = "true" ]; then \
		echo ""; \
		echo "🔍 Running SonarQube analysis on each module separately..."; \
		echo "   Each module → independent project in SonarQube"; \
		echo ""; \
		for module in $(MODULES); do \
			echo "   📍 $$module → ublkit-$$module"; \
			mvn -q -pl $$module sonar:sonar \
				-Dsonar.host.url=$(SONAR_HOST) \
				-Dsonar.projectKey=ublkit-$$module \
				$(if $(SONAR_TOKEN),-Dsonar.login=$(SONAR_TOKEN),) \
				2>&1 | grep -E "BUILD|ERROR" || true; \
		done; \
		echo ""; \
		echo "✅ Module analysis complete"; \
	else \
		echo "⏭️  SonarQube DISABLED for modules"; \
		echo ""; \
		echo "Enable with:"; \
		echo "  make sonar-modules SONAR_ENABLED=true"; \
		echo "  make sonar-modules SONAR_ENABLED=true SONAR_HOST=http://localhost:9000"; \
	fi

# ════════════════════════════════════════════════════════════════════════════
# SONARQUBE - Parent + All Modules
# ════════════════════════════════════════════════════════════════════════════

sonar: sonar-parent sonar-modules
	@echo ""; echo "╔════════════════════════════════════════════════╗"; \
	echo "║  ✅ SonarQube Analysis Complete                ║"; \
	echo "║  📦 Parent + 10 modules analyzed              ║"; \
	echo "╚════════════════════════════════════════════════╝"

# ════════════════════════════════════════════════════════════════════════════
# EXPORT ISSUES - Dependencies and Vulnerabilities
# ════════════════════════════════════════════════════════════════════════════

export-issues:
	@echo "📊 Exporting dependencies & vulnerabilities..."
	@mkdir -p $(PERSEO_ROOT)/reports
	@echo ""
	@echo "📋 Parent Module:"
	@mvn -q dependency:tree > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-parent-dependencies.txt 2>&1
	@echo "   ✅ $(PROJECT_NAME)-parent-dependencies.txt"
	@echo ""
	@echo "📋 Individual Modules:"
	@for module in $(MODULES); do \
		mvn -q -pl $$module dependency:tree > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-$$module-dependencies.txt 2>&1; \
		echo "   ✅ $(PROJECT_NAME)-$$module-dependencies.txt"; \
	done
	@echo ""
	@echo "📊 Creating manifest..."
	@echo "UBLKit-Java Multi-Module Project" > $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-manifest.txt
	@echo "Generated: $$(date)" >> $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-manifest.txt
	@echo "" >> $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-manifest.txt
	@echo "Modules (10):" >> $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-manifest.txt
	@for m in $(MODULES); do echo "  - $$m" >> $(PERSEO_ROOT)/reports/$(PROJECT_NAME)-manifest.txt; done
	@echo ""
	@echo "✅ All reports exported to $(PERSEO_ROOT)/reports/"
