import os
import re

template_dir = 'ublkit-render/src/main/resources/templates'

for filename in os.listdir(template_dir):
    if not filename.endswith('.html') or 'ticket' not in filename:
        continue

    filepath = os.path.join(template_dir, filename)
    with open(filepath, 'r') as f:
        content = f.read()

    # Find :root { ... }
    root_match = re.search(r'\s*:root\s*\{\s*(.*?)\s*\}', content)
    if root_match:
        vars_str = root_match.group(1)
        vars_dict = {}
        for var_decl in vars_str.split(';'):
            var_decl = var_decl.strip()
            if var_decl:
                parts = var_decl.split(':')
                if len(parts) == 2:
                    var_name = parts[0].strip()
                    var_val = parts[1].strip()
                    vars_dict[var_name] = var_val

        # Remove :root block
        content = content[:root_match.start()] + content[root_match.end():]

        # Replace var(--var-name) with value
        for var_name, var_val in vars_dict.items():
            content = content.replace(f'var({var_name})', var_val)

        with open(filepath, 'w') as f:
            f.write(content)
        print(f"Fixed {filename}")
