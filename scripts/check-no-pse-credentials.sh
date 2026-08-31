#!/usr/bin/env bash
set -euo pipefail

found=0
while IFS= read -r -d '' path; do
  normalized=$(printf '%s' "$path" | tr '[:upper:]' '[:lower:]')
  case "$normalized" in
    docs/pse/*credencial*|docs/pse/*credential*)
      printf 'ERROR: forbidden provider credential path is tracked: %s\n' "$path" >&2
      found=1
      ;;
  esac
done < <(git ls-files -z -- 'docs/pse/**')

if (( found != 0 )); then
  printf 'Keep provider credentials in the approved secret manager, never under docs/pse/.\n' >&2
  exit 1
fi

printf 'OK: no provider credential paths are tracked under docs/pse/.\n'
