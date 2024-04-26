folder('Tools') {
    description('Folder for miscellaneous tools.')
}

job('Tools/clone-repository') {
    description('Clone a Git repository.')
    parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
    }
    steps {
        shell('git clone $GIT_REPOSITORY_URL')
    }
    wrappers {
        preBuildCleanup()
    }
    triggers {}œ
}

job('Tools/SEED') {
    description('Create a job.')
    parameters {
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: "EpitechIT31000/chocolatine")')
        stringParam('DISPLAY_NAME', '', 'Display name for the job')
    }
    steps {
        dsl {
            text('''
                job(DISPLAY_NAME) {
                    description('Job created by SEED.')
                    properties {
                        githubProjectUrl("https://github.com/" + GITHUB_NAME)
                    }
                    scm {
                        git {
                            remote {
                                url("https://github.com/" + GITHUB_NAME + ".git")
                            }
                            branch('master')
                        }
                    }
                    triggers {
                        scm('* * * * *')
                    }
                    wrappers {
                        preBuildCleanup()
                    }
                    steps {
                        shell('make fclean')
                        shell('make')
                        shell('make tests_run')
                        shell('make clean')
                    }
                }
            '''.stripIndent())
        }
    }
    triggers {}
}